package org.filip.springbootstartstructure.mappers;

import org.filip.springbootstartstructure.domain.User;
import org.filip.springbootstartstructure.web.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Optional;

@Component
public class UserDtoToUser {

    private ModelMapper modelMapper;

    private User user;

    public UserDtoToUser(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.user = new User();
    }

    public User convert(UserDto userDto) {
        Optional<UserDto> userDtoOptional = Optional.ofNullable(userDto);
        if (userDtoOptional.isPresent()) {
            user = modelMapper.map(userDtoOptional.get(), User.class);
        }

        return user;
    }


}
