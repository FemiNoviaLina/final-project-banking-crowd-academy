package com.fnvls.postservice.impl.service;

import com.fnvls.postservice.api.dto.input.PostInputDto;
import com.fnvls.postservice.api.dto.output.CategoryOutputDto;
import com.fnvls.postservice.api.dto.output.PostCategoryOutputDto;
import com.fnvls.postservice.api.dto.output.PostOutputDto;
import com.fnvls.postservice.api.service.PostService;
import com.fnvls.postservice.data.Category;
import com.fnvls.postservice.data.Post;
import com.fnvls.postservice.impl.exception.FileStorageException;
import com.fnvls.postservice.impl.exception.MyFileNotFoundException;
import com.fnvls.postservice.impl.property.FileStorageProperty;
import com.fnvls.postservice.impl.repository.CategoryRepository;
import com.fnvls.postservice.impl.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final Path fileStorageLocation;

    @Autowired
    public PostServiceImpl(FileStorageProperty fileStorageProperty) {
        this.fileStorageLocation = Paths.get(fileStorageProperty.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public PostOutputDto createPost(String id, PostInputDto input) {
        List<String> images = new ArrayList<>();
        if(input.getImages() != null) {
            for(int i = 0; i < input.getImages().size(); i++) {
                images.add("/post/image/" + storeImage(input.getImages().get(i), i));
            };
        } else images = null;

        Post post = Post.builder()
                .userId(Long.parseLong(id))
                .content(input.getContent())
                .images(images)
                .categoriesId(input.getCategoriesId())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        postRepository.save(post);

        List<PostCategoryOutputDto> postCategory = new ArrayList<>();
        for (String category: post.getCategoriesId()) {
            postCategory.add(modelMapper.map(categoryRepository.findById(category).get(), PostCategoryOutputDto.class));
        }

        PostOutputDto out = modelMapper.map(post, PostOutputDto.class);
        out.setCategories(postCategory);
        return out;
    }

    @Override
    public List<PostOutputDto> getPosts(List<String> category, Integer limit, Integer offset) {
        Pageable paging = PageRequest.of(offset, limit);
        Page<Post> postsPage;
        if(category.size() != 0) postsPage = postRepository.findByCategoriesIdIn(category, paging);
        else postsPage = postRepository.findAll(paging);

        List<Post> posts = postsPage.getContent();
        List<PostOutputDto> out = new ArrayList<>();
        for (Post post : posts) {
            PostOutputDto outDto = modelMapper.map(post, PostOutputDto.class);
            List<PostCategoryOutputDto> postCategory = new ArrayList<>();
            for (String categoryId: post.getCategoriesId()) {
                postCategory.add(modelMapper.map(categoryRepository.findById(categoryId).get(), PostCategoryOutputDto.class));
            }
            outDto.setCategories(postCategory);
            out.add(outDto);
        }

        return out;
    }

    @Override
    public PostOutputDto getPost(String id) {
        Optional<Post> post = postRepository.findById(id);

        if(post.isEmpty()) return null;

        List<PostCategoryOutputDto> postCategory = new ArrayList<>();
        for (String category: post.get().getCategoriesId()) {
            postCategory.add(modelMapper.map(categoryRepository.findById(category).get(), PostCategoryOutputDto.class));
        }

        PostOutputDto out = modelMapper.map(post.get(), PostOutputDto.class);
        out.setCategories(postCategory);

        return out;
    }

    @Override
    public Resource getImagePost(String fileName) {
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

    public String storeImage(MultipartFile file, Integer index) {
        String fileExtention = StringUtils.cleanPath(file.getOriginalFilename()).split("\\.")[1];

        String fileName = "image-post" + index + Long.toString(new Date().getTime()) + "." +fileExtention;

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
}
