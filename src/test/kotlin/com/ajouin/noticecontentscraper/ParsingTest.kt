package com.ajouin.noticecontentscraper

import com.ajouin.noticecontentscraper.dto.ContentRequest
import com.ajouin.noticecontentscraper.scraper.ScraperFactory
import com.ajouin.noticecontentscraper.entity.NoticeType.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class ParsingTest @Autowired constructor(
    private val scraperFactory: ScraperFactory,
) {

    @Test
    @DisplayName("신버전 파싱 테스트")
    fun test1() {
        val scraper = scraperFactory.getScraper(일반공지)
            ?: throw Exception()
        val contentRequest = ContentRequest(
            title = "AJOU=∞, 충주맨 <홍보의 신> 사인북 이벤트 당첨 및 소감문 이벤트 안내",
            link = "https://www.ajou.ac.kr/kr/ajou/notice.do?mode=view&articleNo=291630&article.offset=10&articleLimit=10",
            isTopFixed = true,
            noticeType = 일반공지,
            fetchId = 1L,
            id = 1L,
            views = 0L,
        )
        val content = scraper.fetch(contentRequest).let {
            scraper.parse(it)
        }

        assertThat(content.images.size).isEqualTo(2)
        assertThat(content.content).isNotBlank()
    }

    @Test
    @Disabled
    @DisplayName("구버전 파싱 테스트")
    fun test2() {
        val scraper = scraperFactory.getScraper(생활관)
            ?: throw Exception()
        val contentRequest = ContentRequest(
            title = "☆2024-1학기 (4차) 잔여석 추가 모집☆",
            link = "https://dorm.ajou.ac.kr/dorm/board/board01.jsp?mode=view&article_no=239036&board_wrapper=%2Fdorm%2Fboard%2Fboard01.jsp&pager.offset=0&board_no=774",
            isTopFixed = true,
            noticeType = 생활관,
            fetchId = 1L,
            id = 1L,
            views = 0L,
        )
        val content = scraper.fetch(contentRequest).let {
            scraper.parse(it)
        }
        assertThat(content.images.size).isEqualTo(5)
        assertThat(content.content).isNotBlank()
    }

    @Test
    @DisplayName("간호대학, 의과대학 파싱 테스트")
    fun test3() {
        val scraper = scraperFactory.getScraper(간호대학)
            ?: throw Exception()
        val contentRequest = ContentRequest(
            title = "2024-1학기 대학원 간호학과 수업시간표(안)-강의실 추후 공지",
            link = "https://www.ajoumc.or.kr/nursing/board/commBoardNRNewsView.do?page=3&no=67685",
            isTopFixed = true,
            noticeType = 간호대학,
            fetchId = 1L,
            id = 1L,
            views = 0L,
        )
        val content = scraper.fetch(contentRequest).let {
            scraper.parse(it)
        }
        logger.info { content.content }
        logger.info { content.images }
        assertThat(content.images.size).isEqualTo(1)
        assertThat(content.content).isNotBlank()
    }

    @Test
    @DisplayName("소프트웨어학과 파싱 테스트")
    fun test4() {
        val scraper = scraperFactory.getScraper(소프트웨어학과)
            ?: throw Exception()
        val contentRequest = ContentRequest(
            title = "「2024 핵테온 세종(HackTheon Sejong)」국제 대학생 사이버보안 경진대회(신청 기간: 2024. 3. 25. ~ 4. 21.)",
            link = "http://software.ajou.ac.kr/bbs/board.php?tbl=notice&mode=VIEW&num=1377&category=&findType=&findWord=&sort1=&sort2=&it_id=&shop_flag=&mobile_flag=&page=1",
            isTopFixed = true,
            noticeType = 소프트웨어학과,
            fetchId = 1L,
            id = 1L,
            views = 0L,
        )
        val content = scraper.fetch(contentRequest).let {
            scraper.parse(it)
        }
        logger.info { content.content }
        logger.info { content.images }
        assertThat(content.images.size).isEqualTo(2)
        assertThat(content.content).isNotBlank()
    }
}
