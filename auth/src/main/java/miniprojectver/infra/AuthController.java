package miniprojectver.infra;

import miniprojectver.domain.Member;
import miniprojectver.domain.MemberRepository;
import miniprojectver.domain.YesNo;
import miniprojectver.infra.dto.LoginRequestDto;
import miniprojectver.infra.dto.SignUpRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto requestDto) {
        
        Member member = new Member();
        member.setLoginId(requestDto.getLoginId());
        member.setName(requestDto.getName());
        member.setRole(requestDto.getRole());
        member.setStatus(requestDto.getStatus());

        // --- 이 부분이 에러를 해결하는 핵심 로직입니다 ---
        // DTO로부터 받은 Boolean 값을 YesNo Enum 타입으로 변환합니다.
        YesNo isKt = (requestDto.getIsKtCustomer() != null && requestDto.getIsKtCustomer()) 
                        ? YesNo.YES 
                        : YesNo.NO;
        member.setIsKtCustomer(isKt); // 변환된 Enum 값을 설정합니다.
        // ---------------------------------------------

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        member.setPassword(encodedPassword);

        Member savedMember = memberRepository.save(member);
        return ResponseEntity.ok(savedMember);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginRequest.getLoginId());

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
                return ResponseEntity.ok(member);
            }
        }
        return ResponseEntity.status(401).body("로그인 정보가 올바르지 않습니다.");
    }
}