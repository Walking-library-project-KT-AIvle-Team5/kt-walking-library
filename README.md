# kt-walking-library
<div align="center">
  <h1>KT 걷다가서재: AI 기반 자동 출간 및 구독 플랫폼</h1>
  <p><strong>KT AIVLE School 5차 클라우드 네이티브 앱 개발 미니프로젝트</strong></p>
  <p>
    <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white" />
    <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" />
    <img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black" />
    <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" />
    <img src="https://img.shields.io/badge/Kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white" />
    <img src="https://img.shields.io/badge/Azure-0078D4?style=for-the-badge&logo=microsoft-azure&logoColor=white" />
  </p>
</div>

---

## 📖 프로젝트 개요 (Project Overview)

본 프로젝트는 **"AI 기반 자동 출간 및 구독 플랫폼"**인 'KT 걷다가서재'를 클라우드 네이티브 환경에서 개발하는 것을 목표로 합니다. [1]
도메인 주도 설계(DDD)를 바탕으로 마이크로서비스 아키텍처(MSA)를 분석 및 설계하고, Spring Boot와 React를 사용하여 구현합니다. 최종적으로는 Azure Kubernetes Service(AKS)에 배포하고, CI/CD 파이프라인과 모니터링 시스템을 구축하여 안정적인 운영 환경을 마련합니다. [1]

---

## 👥 팀원 및 역할 (Team & Roles)

프로젝트의 성공적인 수행을 위해 다음과 같이 역할을 분담합니다. [1]

| 역할 (Role) | 담당자 (Member) | 주요 업무 (Responsibilities) |
| :--- | :--- | :--- |
| 👨‍💻 **서브 도메인 Owner** | `남경탁(조장)` | 분석/설계된 개별 마이크로서비스의 구현 및 배포/운영 담당 |
| ⚙️ **협업 환경 구성자 (Git Repo Owner)** | `김강민` | 소스 코드 저장소(Repo) 총괄, 멤버 초대 및 브랜치 전략 관리 |
| ☁️ **쿠버네티스 클러스터 관리자** | `[정수영]` | 마이크로서비스 배포를 위한 쿠버네티스(AKS) 클러스터 생성 및 관리 |
| 📦 **컨테이너 레지스트리 관리자** | `[나성원]` | 컨테이너 이미지(Docker Image)를 위한 레지스트리(ACR) 생성 및 관리 |
| 🚀 **파이프라인 관리자** | `[박채은]` | CI/CD 배포 자동화를 위한 파이프라인 생성 및 관리 |
| 📊 **모니터링 서버 담당자** | `[채윤승]` | 서비스 모니터링(Prometheus, Grafana) 및 로깅(Loki) 서버 설치 및 관리 |

---

## 🛠️ 기술 스택 (Tech Stack)

| 구분 | 기술 |
| :--- | :--- |
| **Analysis & Design** | `Domain-Driven Design` `Event Storming` `User Story` |
| **Backend** | `Java` `Spring Boot` |
| **Frontend** | `React` `JavaScript` |
| **API Gateway** | `Spring Cloud Gateway` |
| **Messaging** | `Apache Kafka` |
| **Containerization** | `Docker` |
| **Orchestration** | `Kubernetes (Azure AKS)` |
| **CI/CD** | `GitHub Actions` `Azure Pipelines` |
| **Monitoring** | `Prometheus` `Grafana` `Istio` `Loki` |
| **Cloud** | `Microsoft Azure` |

---



