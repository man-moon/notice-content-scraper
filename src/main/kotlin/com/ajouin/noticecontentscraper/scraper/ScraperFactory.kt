package com.ajouin.noticecontentscraper.scraper

import com.ajouin.noticecontentscraper.scraper.impl.portal.DormitoryNoticeScraper
import com.ajouin.noticecontentscraper.scraper.impl.portal.GeneralNoticeScraper
import com.ajouin.noticecontentscraper.scraper.impl.portal.ScholarshipNoticeScraper
import com.ajouin.noticecontentscraper.entity.NoticeType
import com.ajouin.noticecontentscraper.entity.NoticeType.*
import com.ajouin.noticecontentscraper.scraper.impl.college.*
import com.ajouin.noticecontentscraper.scraper.impl.department.*
import org.springframework.stereotype.Component

@Component
class ScraperFactory {
    private val scrapers: Map<NoticeType, NoticeScraper> = mapOf(

        // 포탈
        일반공지 to GeneralNoticeScraper(),
        장학공지 to ScholarshipNoticeScraper(),
        생활관 to DormitoryNoticeScraper(),

        // 단과대
        공과대학 to EngineeringNoticeScraper(),
        정보통신대학 to InformationNoticeScraper(),
        소프트웨어융합대학 to SoftwareConvergenceNoticeScraper(),
        자연과학대학 to NaturalScienceNoticeScraper(),
        경영대학 to BusinessCollegeNoticeScraper(),
        인문대학 to HumanityNoticeScraper(),
        사회과학대학 to SocialScienceNoticeScraper(),
        의과대학 to MedicineNoticeScraper(),
        간호대학 to NursingScraper(),
        약학대학 to PharmacyNoticeScraper(),

        // 학과
        // 공화대학
        기계공학과 to MechanicalEngineeringNoticeScraper(),
        산업공학과 to IndustrialEngineeringNoticeScraper(),
        응용화학생명공학과 to AppliedChemistryBiologicalNoticeScraper(),
        화학공학과 to ChemicalEngineeringNoticeScraper(),
        첨단신소재공학과 to AdvancedMaterialsEngineeringNoticeScraper(),
        환경안전공학과 to EnvironmentSafetyEngineeringNoticeScraper(),
        건설시스템공학과 to ConstructionSystemsEngineeringNoticeScraper(),
        교통시스템공학과 to TransportationSystemsEngineeringNoticeScraper(),
        건축학과 to ArchitecturalNoticeScraper(),
        융합시스템공학과 to ConvergenceSystemsEngineeringNoticeScraper(),
        AI모빌리티공학과 to AiMobilityEngineeringNoticeScraper(),
        전자공학과 to ElectricEngineeringNoticeScraper(),
        지능형반도체공학과 to IntelligentSemiconductorEngineeringNoticeScraper(),
        소프트웨어학과 to SoftwareNoticeScraper(),
        인공지능융합학과 to ArtificialIntelligenceConvergenceNoticeScraper(),
        사이버보안학과 to CyberSecurityNoticeScraper(),
        디지털미디어학과 to DigitalMediaNoticeScraper(),
        국방디지털융합학과 to DefenseDigitalConvergenceNoticeScraper(),
        수학과 to MathNoticeScraper(),
        물리학과 to PhysicsNoticeScraper(),
        화학과 to ChemistryNoticeScraper(),
        생명과학과 to BiologyNoticeScraper(),
        경영학과 to BusinessNoticeScraper(),
        e비즈니스학과 to EBusinessNoticeScraper(),
        금융공학과 to FinancialEngineeringNoticeScraper(),
        글로벌경영학과 to GlobalManagementNoticeScraper(),
        국어국문학과 to KoreanLanguageLiteratureNoticeScraper(),
        영어영문학과 to EnglishLanguageLiteratureNoticeScraper(),
        불어불문학과 to FrenchLanguageLiteratureNoticeScraper(),
        사학과 to HistoryNoticeScraper(),
        문화콘텐츠학과 to CulturalContentsNoticeScraper(),
        경제학과 to EconomicsNoticeScraper(),
        행정학과 to PublicAdministrationNoticeScraper(),
        심리학과 to PsychologyNoticeScraper(),
        사회학과 to SociologyNoticeScraper(),
        정치외교학과 to PoliticalScienceDiplomacyNoticeScraper(),
        스포츠레저학과 to SportsLeisureNoticeScraper(),

    )

    fun getScraper(noticeType: NoticeType): NoticeScraper? = scrapers[noticeType]
}