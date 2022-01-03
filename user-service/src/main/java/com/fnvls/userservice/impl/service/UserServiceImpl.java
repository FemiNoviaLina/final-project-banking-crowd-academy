package com.fnvls.userservice.impl.service;

import com.fnvls.userservice.api.dto.input.UserProfileInputDto;
import com.fnvls.userservice.api.dto.output.AuthUserOutputDto;
import com.fnvls.userservice.api.dto.output.UserBasicInfoDto;
import com.fnvls.userservice.api.dto.output.UserProfileOutputDto;
import com.fnvls.userservice.api.service.UserService;
import com.fnvls.userservice.data.User;
import com.fnvls.userservice.data.UserProfile;
import com.fnvls.userservice.impl.exception.FileStorageException;
import com.fnvls.userservice.impl.exception.MyFileNotFoundException;
import com.fnvls.userservice.impl.property.FileStorageProperty;
import com.fnvls.userservice.impl.repository.UserProfileRepository;
import com.fnvls.userservice.impl.repository.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final Path fileStorageLocation;

    @Autowired
    public UserServiceImpl(FileStorageProperty fileStorageProperty) {
        this.fileStorageLocation = Paths.get(fileStorageProperty.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public List<AuthUserOutputDto> getUnapprovedUsers(Integer limit, Integer offset) {
        Pageable paging = PageRequest.of(offset, limit);
        Page<User> usersPage = userRepository.findByEnabledAndRoleIn(Boolean.FALSE, List.of("learner", "trainer"), paging);

        List<User> users = usersPage.getContent();
        List<AuthUserOutputDto> out = new ArrayList<>();
        for (User user : users) {
            out.add(modelMapper.map(user, AuthUserOutputDto.class));
        }

        return out;
    }

    @Override
    public void approveUsersApplication(List<Long> input) {
        input.forEach(idInput -> {
            Optional<User> temp = userRepository.findById(idInput);
            temp.ifPresent(user -> {
                user.setEnabled(true);
                userRepository.save(user);
            });
        });
    }

    @Override
    public UserProfileOutputDto updateUserProfile(Long id, UserProfileInputDto input) {
        Optional<UserProfile> tempUserProfile = userProfileRepository.findById(id);
        UserProfile userProfile;

        if(tempUserProfile.isEmpty()) {
            userProfile = modelMapper.map(input, UserProfile.class);
        } else {
            userProfile = tempUserProfile.get();
            modelMapper.map(input, userProfile);
        }

        userProfile.setId(id);
        String profilePic = input.getProfilePic() == null ? null : "/user/profile-pic/" + storeImage(input.getProfilePic());
        if(profilePic != null) userProfile.setProfilePic(profilePic);

        UserProfile userProfileRes = userRepository.findById(id).map(user -> {
                    userProfile.setUser(user);
                    return userProfileRepository.save(userProfile);
        }).orElseThrow(() -> new NotFoundException("User not found!"));

        UserProfileOutputDto out = modelMapper.map(userProfileRes,UserProfileOutputDto.class);
        return out;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findDistinctUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Not found!");
        }

        return user;
    }

    public String storeImage(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    @Override
    public UserProfileOutputDto getUserProfile(Long id) {
        Optional<User> tempUser = userRepository.findById(id);
        
        if(tempUser.isEmpty()) return null;

        Optional<UserProfile> tempUserProfile = userProfileRepository.findById(id);

        UserProfileOutputDto out = UserProfileOutputDto.builder().build();

        if(!tempUserProfile.isEmpty()) modelMapper.map(tempUserProfile.get(), out);
        UserBasicInfoDto userBasicInfo = modelMapper.map(tempUser.get(), UserBasicInfoDto.class);
        out.setUserBasicInfo(userBasicInfo);

        System.out.println(out);
        return out;
    }

    @Override
    public UserBasicInfoDto getUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) return null;

        UserBasicInfoDto out = modelMapper.map(user.get(), UserBasicInfoDto.class);

        return out;
    }
}
