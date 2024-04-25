package ru.anykeyers.videoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.videoservice.domain.report.Report;

/**
 * DAO для работы с жалобами
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
