// ✅ dashboard/domain/BestsellerCountRepository.java
package miniprojectver.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BestsellerCountRepository extends JpaRepository<BestsellerCount, String> { }
