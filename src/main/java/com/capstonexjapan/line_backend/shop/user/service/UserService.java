package com.capstonexjapan.line_backend.shop.user.service;

import com.capstonexjapan.line_backend.shop.product.controller.request.CreateProduct;
import com.capstonexjapan.line_backend.shop.product.entity.Product;
import com.capstonexjapan.line_backend.shop.store.entity.Store;
import com.capstonexjapan.line_backend.shop.user.controller.request.CreateAddressDTO;
import com.capstonexjapan.line_backend.shop.user.controller.request.CreateUserDTO;
import com.capstonexjapan.line_backend.shop.user.controller.response.GetAddressDTO;
import com.capstonexjapan.line_backend.shop.user.controller.response.GetUserDTO;
import com.capstonexjapan.line_backend.shop.user.entity.Address;
import com.capstonexjapan.line_backend.shop.user.entity.UserEntity;
import com.capstonexjapan.line_backend.shop.user.repository.AddressRepository;
import com.capstonexjapan.line_backend.shop.user.repository.UserRepository;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public void createUser(CreateUserDTO dto) {
        userRepository.save(new UserEntity().toEntity(dto));
    }

    public List<GetUserDTO> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(GetUserDTO::toDTO)
                .collect(Collectors.toList());
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public GetUserDTO getUser(Long id) {
        return  GetUserDTO.toDTO(findById(id));
    }

    public void createAddress(CreateAddressDTO dto) {
        addressRepository.save(new Address().toEntity(dto, findById(dto.getUserId())));
    }

    public List<GetAddressDTO> getAddress(Long userId){
        return addressRepository.findByUser(findById(userId))
                .stream()
                .map(GetAddressDTO::toDTO)
                .collect(Collectors.toList());
    }


}
