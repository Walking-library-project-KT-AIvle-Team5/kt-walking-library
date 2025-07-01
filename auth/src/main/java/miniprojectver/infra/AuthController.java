package miniprojectver.infra;

import miniprojectver.domain.Member;
import miniprojectver.domain.MemberRepository;
import miniprojectver.domain.YesNo;
import miniprojectver.infra.dto.SignUpRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

        // isKtCustomer 변환 로직
        YesNo isKt;
        String customerInput = requestDto.getIsKtCustomer().toUpperCase();

        if ("Y".equals(customerInput) || "YES".equals(customerInput) || "TRUE".equals(customerInput)) {
            isKt = YesNo.YES;
        } else if ("N".equals(customerInput) || "NO".equals(customerInput) || "FALSE".equals(customerInput)) {
            isKt = YesNo.NO;
        } else {
            // 잘못된 값이 들어오면, 친절한 에러 메시지와 함께 400 Bad Request 응답을 보냄
            return ResponseEntity.badRequest().body("isKtCustomer 필드는 'Y', 'N', 'YES', 'NO', 'true', 'false' 중 하나여야 합니다.");
        }

        Member member = new Member();
        member.setLoginId(requestDto.getLoginId());
        member.setName(requestDto.getName());
        member.setRole(requestDto.getRole());
        member.setStatus(requestDto.getStatus());
        member.setIsKtCustomer(isKt); // 변환된 Enum 값을 설정

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        member.setPassword(encodedPassword);

        Member savedMember = memberRepository.save(member);
        return ResponseEntity.ok(savedMember); // 성공 시 200 OK 와 함께 사용자 정보 반환
    }
}