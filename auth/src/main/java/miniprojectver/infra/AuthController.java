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
        
        YesNo isKt;
        String customerInput = Optional.ofNullable(requestDto.getIsKtCustomer()).orElse("").toUpperCase();

        if ("Y".equals(customerInput) || "YES".equals(customerInput) || "TRUE".equals(customerInput)) {
            isKt = YesNo.YES;
        } else if ("N".equals(customerInput) || "NO".equals(customerInput) || "FALSE".equals(customerInput) || customerInput.isEmpty()) {
            isKt = YesNo.NO;
        } else {
            return ResponseEntity.badRequest().body("isKtCustomer 필드는 'Y', 'N', 'YES', 'NO', 'true', 'false' 중 하나여야 합니다.");
        }

        Member member = new Member();
        member.setLoginId(requestDto.getLoginId());
        member.setName(requestDto.getName());
        member.setRole(requestDto.getRole());
        member.setIsKtCustomer(isKt); 

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        member.setPassword(encodedPassword);

        Member savedMember = memberRepository.save(member);

        return ResponseEntity.ok(savedMember);
    }
    
    // login 메소드를 완전히 삭제합니다. 이 역할은 Spring Security가 담당하게 됩니다.
    
    @PostMapping("/members/{id}/verify-kt")
    public ResponseEntity<?> verifyKtCustomer(@PathVariable Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            
            if (member.getIsKtCustomer() == YesNo.NO) {
                return ResponseEntity.badRequest().body("KT 고객으로 등록된 사용자가 아니므로 인증을 진행할 수 없습니다.");
            }
            
            member.setIsKtCustomer(YesNo.YES);
            Member updatedMember = memberRepository.save(member);
            
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.status(404).body("사용자를 찾을 수 없습니다.");
        }
    }
}
