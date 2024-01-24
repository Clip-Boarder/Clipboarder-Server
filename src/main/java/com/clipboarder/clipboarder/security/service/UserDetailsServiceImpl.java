package com.clipboarder.clipboarder.security.service;

import com.clipboarder.clipboarder.entity.ClipboarderUser;
import com.clipboarder.clipboarder.entity.dto.ClipboarderUserDTO;
import com.clipboarder.clipboarder.repository.ClipboarderRepository;
import com.clipboarder.clipboarder.security.dto.ClipboarderAuthUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ClipboarderRepository clipboarderRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserDetailsServiceImpl loadbyusername : " + username);

        Optional<ClipboarderUser> result = clipboarderRepository.findByEmail(username);
        if(result.isEmpty())
            throw new UsernameNotFoundException("정보가 없습니다.");

        ClipboarderUser clipboarderUser = result.get();

        log.info("---------------------");
        log.info(clipboarderUser);

        ClipboarderAuthUserDTO clipboarderAuthUserDTO = new ClipboarderAuthUserDTO(
                clipboarderUser.getEmail(),
                clipboarderUser.getPassword(),
                clipboarderUser.getName(),
                clipboarderUser.getPicture(),
                clipboarderUser.getProvider(),
                clipboarderUser.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet())
        );

        return clipboarderAuthUserDTO;
    }
}
