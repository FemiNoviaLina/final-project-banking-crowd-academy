package com.fnvls.userservice.impl.service;

import com.fnvls.userservice.api.dto.output.CategoryOutputDto;
import com.fnvls.userservice.api.dto.output.InterestOutputDto;
import com.fnvls.userservice.api.dto.output.UserBasicInfoDto;
import com.fnvls.userservice.api.response.BaseResponse;
import com.fnvls.userservice.api.service.InterestService;
import com.fnvls.userservice.data.User;
import com.fnvls.userservice.data.UserInterest;
import com.fnvls.userservice.impl.repository.UserInterestRepository;
import com.fnvls.userservice.impl.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InterestServiceImpl implements InterestService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInterestRepository interestRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InterestOutputDto createInterest(Long id, List<String> categoryId) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) return null;

        List<CategoryOutputDto> categories = new ArrayList<>();
        for (String category: categoryId) {
            ResponseEntity<BaseResponse> categoryInput;
            categoryInput = restTemplate
                    .getForEntity("http://127.0.0.1:8763/category/" + category,
                            BaseResponse.class);
            if(categoryInput.hasBody()) {
                if(categoryInput.getBody() != null) {
                    categories.add(modelMapper.map(categoryInput.getBody().getData(), CategoryOutputDto.class));
                    interestRepository.save(UserInterest.builder().userId(id).categoryId(category).build());
                }
            }
        }

        InterestOutputDto out = InterestOutputDto.builder()
                .userBasicInfo(modelMapper.map(user.get(), UserBasicInfoDto.class))
                .categories(categories)
                .build();

        return out;
    }

    @Override
    public InterestOutputDto getInterest(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) return null;

        List<UserInterest> interests = interestRepository.findByUserId(id);

        List<CategoryOutputDto> categories = new ArrayList<>();
        for (UserInterest category: interests) {
            ResponseEntity<BaseResponse> categoryInput = restTemplate
                    .getForEntity("http://127.0.0.1:8763/category/" + category.getCategoryId(), BaseResponse.class);
            if(categoryInput.hasBody()) {
                if(categoryInput.getBody() != null)
                    categories.add(modelMapper.map(categoryInput.getBody().getData(), CategoryOutputDto.class));
            }
        }

        InterestOutputDto out = InterestOutputDto.builder()
                .userBasicInfo(modelMapper.map(user.get(), UserBasicInfoDto.class))
                .categories(categories)
                .build();

        return out;
    }
}