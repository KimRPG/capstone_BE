package com.capstonexjapan.line_backend.shop.user.service;

import com.capstonexjapan.line_backend.shop.user.controller.request.AddressDTO;
import com.capstonexjapan.line_backend.shop.user.controller.request.CreateUserDTO;
import com.capstonexjapan.line_backend.shop.user.controller.response.GetAddressDTO;
import com.capstonexjapan.line_backend.shop.user.controller.response.GetUserDTO;
import com.capstonexjapan.line_backend.shop.user.entity.Address;
import com.capstonexjapan.line_backend.shop.user.entity.UserEntity;
import com.capstonexjapan.line_backend.shop.user.repository.AddressRepository;
import com.capstonexjapan.line_backend.shop.user.repository.UserRepository;
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

    public void createAddress(AddressDTO dto, Long userId) {
        addressRepository.save(new Address().toEntity(dto, findById(userId)));
    }

    public List<GetAddressDTO> getAddress(Long userId){
        return addressRepository.findByUser(findById(userId))
                .stream()
                .map(GetAddressDTO::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
