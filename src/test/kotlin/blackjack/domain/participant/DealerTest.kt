package blackjack.domain.participant

import blackjack.CardFixtures.CLUB_KING
import blackjack.CardFixtures.DIAMOND_ACE
import blackjack.CardFixtures.HEART_SIX
import blackjack.CardFixtures.HEART_TWO
import blackjack.CardFixtures.SPADE_FIVE
import blackjack.CardFixtures.SPADE_TEN
import blackjack.domain.card.Card
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class DealerTest {

    @Test
    fun `딜러는 덱에서 여러 장의 카드를 뽑을 수 있다`() {
        // given
        val dealer = Dealer()

        // when
        val cards = dealer.drawCards(2)

        // then
        cards.forEach { card ->
            assertThat(card).isInstanceOf(Card::class.java)
        }
    }

    @Test
    fun `딜러는 덱에서 한 장의 카드를 뽑을 수 있다`() {
        // given
        val dealer = Dealer()

        // when
        val card = dealer.drawOneCard()

        // then
        assertThat(card).isInstanceOf(Card::class.java)
    }

    @ParameterizedTest
    @MethodSource("패의 합계가 16이하인 케이스")
    fun `딜러는 가진 패의 합계가 16이하이면 1장의 카드를 추가로 받는다`(cards: Array<Card>) {
        // given
        val dealer = Dealer()

        // when
        dealer.addCards(*cards)

        // then
        assertThat(dealer.isDrawable()).isTrue
    }

    @ParameterizedTest
    @MethodSource("패의 합계가 17이상인 케이스")
    fun `딜러는 가진 패의 합계가 17이상이면 카드를 추가로 받을 수 없다`(cards: Array<Card>) {
        // given
        val dealer = Dealer()

        // when
        dealer.addCards(*cards)

        // then
        assertThat(dealer.isDrawable()).isFalse
    }

    @Test
    fun `딜러의 처음 두 장의 카드 합이 21일 경우 BLACKJACK 이다`() {
        // given
        val dealer = Dealer()

        // when
        dealer.addCards(SPADE_TEN, DIAMOND_ACE)

        // then
        assertThat(dealer.status).isEqualTo(ParticipantStatus.BLACKJACK)
    }

    @Test
    fun `딜러의 처음 두 장의 카드 합이 21이 아닌 경우 BLACKJACK 이 아니다`() {
        // given
        val dealer = Dealer()

        // when
        dealer.addCards(SPADE_TEN, CLUB_KING, DIAMOND_ACE)

        // then
        assertThat(dealer.status).isNotEqualTo(ParticipantStatus.BLACKJACK)
    }

    companion object {
        @JvmStatic
        fun `패의 합계가 16이하인 케이스`() = Stream.of(
            Arguments.of(arrayOf(SPADE_TEN, HEART_SIX)),
            Arguments.of(arrayOf(DIAMOND_ACE, SPADE_FIVE, SPADE_TEN)),
            Arguments.of(arrayOf(HEART_TWO))
        )

        @JvmStatic
        fun `패의 합계가 17이상인 케이스`() = Stream.of(
            Arguments.of(arrayOf(SPADE_TEN, CLUB_KING)),
            Arguments.of(arrayOf(SPADE_TEN, CLUB_KING, DIAMOND_ACE)),
            Arguments.of(arrayOf(CLUB_KING, SPADE_FIVE, HEART_TWO))
        )
    }
}
