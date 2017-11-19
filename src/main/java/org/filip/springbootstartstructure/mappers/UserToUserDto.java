package org.filip.springbootstartstructure.mappers;

import org.filip.springbootstartstructure.domain.User;
import org.filip.springbootstartstructure.web.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserToUserDto {

    private ModelMapper modelMapper;

    private UserDto userDto;

    public UserToUserDto(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.userDto = new UserDto();
    }

    public UserDto convert(User user) {
        Optional<User> userOptional = Optional.ofNullable(user);
        if (userOptional.isPresent()) {
            userDto = modelMapper.map(userOptional.get(), UserDto.class);
        }
        return userDto;
    }


}
