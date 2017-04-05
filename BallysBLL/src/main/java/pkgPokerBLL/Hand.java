package pkgPokerBLL;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import pkgException.HandException;
import pkgPokerEnum.eCardNo;
import pkgPokerEnum.eHandStrength;
import pkgPokerEnum.eRank;
import pkgPokerEnum.eSuit;

public class Hand {

	private UUID HandID;
	private boolean bIsScored;
	private HandScore HS;
	private ArrayList<Card> CardsInHand = new ArrayList<Card>();

	public Hand() {

	}

	public void AddCardToHand(Card c) {
		CardsInHand.add(c);
	}

	public ArrayList<Card> getCardsInHand() {
		return CardsInHand;
	}

	public HandScore getHandScore() {
		return HS;
	}

	public void AddToCardsInHand(Card c) {
		CardsInHand.add(c);
	}

	public Hand EvaluateHand() {

		Hand h = null;

		ArrayList<Hand> ExplodedHands = ExplodeHands(this);

		for (Hand hand : ExplodedHands) {
			hand = Hand.EvaluateHand(hand);
		}

		// Figure out best hand
		Collections.sort(ExplodedHands, Hand.HandRank);

		// Return best hand.
		// TODO: Fix... what to do if there is a tie?
		return ExplodedHands.get(0);
	}

	// TODO: one hand is passed in, 1, 52, 2704, etc are passed back
	// No jokers, 'ReturnHands' should have one hand
	// One Wild/joker 'ReturnHands' should have 52 hands, etc

	// ExplodeHands will do that whole thing with all of the crap that we
	// figured out monday night
	public static ArrayList<Hand> ExplodeHands(Hand h) {

		ArrayList<Hand> ReturnHands = new ArrayList<Hand>();

		int scount = 0;
		for (Card card : h.getCardsInHand()) {
			if ((card.geteRank() == eRank.JOKER) || (card.isWild() == true))
				scount++;

		}
		// removes jokers from the front
		int i = scount;
		while (i > 0) {
			h.getCardsInHand().remove(0);
			i = i - 1;
			}
		// builds a new ArrayList of hands based on number of free spaces
		if (scount == 1) {
			for (eSuit suit : eSuit.values()) {
				for (eRank rank : eRank.values()) {
					h.getCardsInHand().add(new Card(rank, suit, 0));
					ReturnHands.add(h);
					h.getCardsInHand().remove(4);
				}
			}
		}
		if (scount == 2) {
			for (eSuit suit : eSuit.values()) {
				for (eRank rank : eRank.values()) {
					for (eSuit suit1 : eSuit.values()) {
						for (eRank rank1 : eRank.values()) {
							h.getCardsInHand().add(new Card(rank1, suit1, 0));
							h.getCardsInHand().add(new Card(rank, suit, 1));
							ReturnHands.add(h);
							h.getCardsInHand().remove(4);
							h.getCardsInHand().remove(3);
						}
					}
				}
			}
		}

		if (scount == 3) {
			for (eSuit suit : eSuit.values()) {
				for (eRank rank : eRank.values()) {
					for (eSuit suit1 : eSuit.values()) {
						for (eRank rank1 : eRank.values()) {
							for (eSuit suit2 : eSuit.values()) {
								for (eRank rank2 : eRank.values()) {
									h.getCardsInHand().add(new Card(rank2, suit2, 0));
									h.getCardsInHand().add(new Card(rank1, suit1, 1));
									h.getCardsInHand().add(new Card(rank, suit, 2));
									ReturnHands.add(h);
									h.getCardsInHand().remove(4);
									h.getCardsInHand().remove(3);
									h.getCardsInHand().remove(2);
								}
							}
						}
					}
				}
			}
		}
		if (scount == 4) {
			for (eSuit suit : eSuit.values()) {
				for (eRank rank : eRank.values()) {
					for (eSuit suit1 : eSuit.values()) {
						for (eRank rank1 : eRank.values()) {
							for (eSuit suit2 : eSuit.values()) {
								for (eRank rank2 : eRank.values()) {
									for (eSuit suit3 : eSuit.values()) {
										for (eRank rank3 : eRank.values()) {
											h.getCardsInHand().add(new Card(rank3, suit3, 0));
											h.getCardsInHand().add(new Card(rank2, suit2, 1));
											h.getCardsInHand().add(new Card(rank1, suit1, 2));
											h.getCardsInHand().add(new Card(rank, suit, 2));
											ReturnHands.add(h);
											h.getCardsInHand().remove(4);
											h.getCardsInHand().remove(3);
											h.getCardsInHand().remove(2);
											h.getCardsInHand().remove(1);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (scount == 5) {
			for (eSuit suit : eSuit.values()) {
				for (eRank rank : eRank.values()) {
					for (eSuit suit1 : eSuit.values()) {
						for (eRank rank1 : eRank.values()) {
							for (eSuit suit2 : eSuit.values()) {
								for (eRank rank2 : eRank.values()) {
									for (eSuit suit3 : eSuit.values()) {
										for (eRank rank3 : eRank.values()) {
											for (eSuit suit4 : eSuit.values()) {
												for (eRank rank4 : eRank.values()) {
													h.getCardsInHand().add(new Card(rank4, suit4, 0));
													h.getCardsInHand().add(new Card(rank3, suit3, 1));
													h.getCardsInHand().add(new Card(rank2, suit2, 2));
													h.getCardsInHand().add(new Card(rank1, suit1, 3));
													h.getCardsInHand().add(new Card(rank, suit, 4));
													ReturnHands.add(h);
													h.getCardsInHand().remove(4);
													h.getCardsInHand().remove(3);
													h.getCardsInHand().remove(2);
													h.getCardsInHand().remove(1);
													h.getCardsInHand().remove(0);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return ReturnHands;

	}

	private static Hand EvaluateHand(Hand h) {

		Collections.sort(h.getCardsInHand());

		// Another way to sort
		// Collections.sort(h.getCardsInHand(), Card.CardRank);

		HandScore hs = new HandScore();
		try {
			Class<?> c = Class.forName("pkgPokerBLL.Hand");

			for (eHandStrength hstr : eHandStrength.values()) {
				Class[] cArg = new Class[2];
				cArg[0] = pkgPokerBLL.Hand.class;
				cArg[1] = pkgPokerBLL.HandScore.class;

				Method meth = c.getMethod(hstr.getEvalMethod(), cArg);
				Object o = meth.invoke(null, new Object[] { h, hs });

				// If o = true, that means the hand evaluated- skip the rest of
				// the evaluations
				if ((Boolean) o) {
					break;
				}
			}

			h.bIsScored = true;
			h.HS = hs;

		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return h;
	}

	private static boolean hasFiveCards(ArrayList<Card> c) throws HandException{
		if(c.size()<5){
			throw new HandException("Must have five cards in hand!");
		}
		else{
			return true;
		}
	}

	public static boolean isFiveOfAKind(ArrayList<Card> cards) throws HandException {
		if(!hasFiveCards(cards)){
			return false;
		}
		boolean isFiveOfAKind = false;
		if ((cards.get(eCardNo.FirstCard.getCardNo()).geteRank()) == (cards.get(eCardNo.FifthCard.getCardNo())
				.geteRank())) {
			isFiveOfAKind = true;
		}
		return isFiveOfAKind;
	}

	public static boolean isStraight(ArrayList<Card> cards, Card c)throws HandException {
		if(!hasFiveCards(cards)){
			return false;
		}
		
		boolean isStraight = false;
		int iStartCard = (Hand.isAce(cards)) ? eCardNo.SecondCard.getCardNo() : eCardNo.FirstCard.getCardNo();

		for (; iStartCard < 4; iStartCard++) {
			if (cards.get(iStartCard).geteRank().getiRankNbr() - 1 == cards.get(iStartCard + 1).geteRank()
					.getiRankNbr()) {
				isStraight = true;
			} else {
				isStraight = false;
				break;
			}
		}

		if (isStraight) {
			if (cards.get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE) {
				if (cards.get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.KING) {
					c.seteRank(cards.get(eCardNo.FirstCard.getCardNo()).geteRank());
					c.seteSuit(cards.get(eCardNo.FirstCard.getCardNo()).geteSuit());
				} else if (cards.get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.FIVE) {
					c.seteRank(cards.get(eCardNo.SecondCard.getCardNo()).geteRank());
					c.seteSuit(cards.get(eCardNo.SecondCard.getCardNo()).geteSuit());

				}
			} else {
				c.seteRank(cards.get(eCardNo.FirstCard.getCardNo()).geteRank());
				c.seteSuit(cards.get(eCardNo.FirstCard.getCardNo()).geteSuit());
			}
		}

		return isStraight;
	}

	public static boolean isAce(ArrayList<Card> cards)throws HandException {
		if(!hasFiveCards(cards)){
			return false;
		}
		if ((cards.get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE)
				&& (cards.get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.KING)
				|| (cards.get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.FIVE)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isFlush(ArrayList<Card> cards) throws HandException {
		if(!hasFiveCards(cards)){
			return false;
		}
		boolean isFlush = false;

		int iCount = 0;
		for (eSuit Suit : eSuit.values()) {
			iCount = 0;
			for (Card c : cards) {
				if (c.geteSuit() == Suit)
					iCount++;
			}

			if (iCount == 5) {
				isFlush = true;
				break;
			}
			if (iCount > 0)
				break;

		}

		return isFlush;

	}

	public static boolean isHandRoyalFlush(Hand h, HandScore hs) throws HandException {
		if(!hasFiveCards(h.getCardsInHand())){
			return false;
		}

		boolean isHandRoyalFlush = false;
		Card c = new Card();

		if ((Hand.isFlush(h.getCardsInHand())) && (Hand.isStraight(h.getCardsInHand(), c))
				&& (Hand.isAce(h.getCardsInHand()))) {
			hs.setHandStrength(eHandStrength.RoyalFlush);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(null);
			isHandRoyalFlush = true;
		}

		return isHandRoyalFlush;
	}

	public static boolean isHandStraightFlush(Hand h, HandScore hs) throws HandException {
		if(!hasFiveCards(h.getCardsInHand())){
			return false;
		}

		boolean isHandStraightFlush = false;
		Card c = new Card();
		if ((Hand.isFlush(h.getCardsInHand())) && (Hand.isStraight(h.getCardsInHand(), c))) {
			hs.setHandStrength(eHandStrength.StraightFlush);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(null);
			isHandStraightFlush = true;
		}

		return isHandStraightFlush;

	}

	
	public static boolean isHandFourOfAKind(Hand h, HandScore hs)throws HandException {
		if(!hasFiveCards(h.getCardsInHand())){
			return false;
		}

		boolean isHandFourOfAKind = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			isHandFourOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			isHandFourOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
		}

		if (isHandFourOfAKind) {
			hs.setHandStrength(eHandStrength.FourOfAKind);
			hs.setLoHand(null);
			hs.setKickers(kickers);
		}

		return isHandFourOfAKind;
	}

	public static boolean isHandFlush(Hand h, HandScore hs)throws HandException {
		if(!hasFiveCards(h.getCardsInHand())){
			return false;
		}

		boolean bIsFlush = false;
		if (isFlush(h.getCardsInHand())) {
			hs.setHandStrength(eHandStrength.Flush);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(null);

			hs.getKickers().add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));

			bIsFlush = true;
		}

		return bIsFlush;
	}

	public static boolean isHandStraight(Hand h, HandScore hs)throws HandException {
		if(!hasFiveCards(h.getCardsInHand())){
			return false;
		}

		boolean bIsStraight = false;
		Card c = new Card();

		if (isStraight(h.getCardsInHand(), c)) {
			hs.setHandStrength(eHandStrength.Straight);
			hs.setHiHand(c.geteRank());
			hs.setLoHand(null);
			bIsStraight = true;
		}
		return bIsStraight;
	}

	public static boolean isHandThreeOfAKind(Hand h, HandScore hs) throws HandException {
		if(!hasFiveCards(h.getCardsInHand())){
			return false;
		}

		boolean isThreeOfAKind = false;
		ArrayList<Card> kickers = new ArrayList<Card>();
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank()) {
			isThreeOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			isThreeOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));

		} else if (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			isThreeOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));

		}

		if (isThreeOfAKind) {
			hs.setHandStrength(eHandStrength.ThreeOfAKind);
			hs.setLoHand(null);
			hs.setKickers(kickers);
		}

		return isThreeOfAKind;
	}

	public static boolean isHandTwoPair(Hand h, HandScore hs) throws HandException {
		if(!hasFiveCards(h.getCardsInHand())){
			return false;
		}

		boolean isHandTwoPair = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FourthCard.getCardNo()).geteRank())) {
			isHandTwoPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));

		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isHandTwoPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));

		} else if ((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isHandTwoPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));

		}

		if (isHandTwoPair) {
			hs.setHandStrength(eHandStrength.TwoPair);
			hs.setKickers(kickers);
		}
		return isHandTwoPair;

	}

	public static boolean isHandPair(Hand h, HandScore hs) throws HandException {
		if(!hasFiveCards(h.getCardsInHand())){
			return false;
		}

		boolean isHandPair = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank()) {
			isHandPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank()) {
			isHandPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		} else if (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			isHandPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		} else if (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			isHandPair = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
		}

		if (isHandPair) {
			hs.setHandStrength(eHandStrength.Pair);
			hs.setLoHand(null);
			hs.setKickers(kickers);
		}

		return isHandPair;

	}

	public static boolean isHandHighCard(Hand h, HandScore hs)throws HandException {
		if(!hasFiveCards(h.getCardsInHand())){
			return false;
		}

		hs.setHandStrength(eHandStrength.HighCard);
		hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		hs.getKickers().add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
		hs.getKickers().add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
		hs.getKickers().add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
		hs.getKickers().add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		hs.setLoHand(null);
		return true;
	}

	public static boolean isAcesAndEights(Hand h, HandScore hs)throws HandException {
		if(!hasFiveCards(h.getCardsInHand())){
			return false;
		}

		boolean isAcesAndEights = false;
		if (Hand.isHandTwoPair(h, hs) == true) {
			if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE)
					&& (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.EIGHT)) {
				hs.setHandStrength(eHandStrength.AcesAndEights);
				isAcesAndEights = true;
			}
		}

		return isAcesAndEights;
	}

	public static boolean isHandFullHouse(Hand h, HandScore hs)throws HandException {
		if(!hasFiveCards(h.getCardsInHand())){
			return false;
		}

		boolean isFullHouse = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;

			hs.setHandStrength(eHandStrength.FullHouse);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());

		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		}

		return isFullHouse;

	}

	public static Comparator<Hand> HandRank = new Comparator<Hand>() {

		public int compare(Hand h1, Hand h2) {

			int result = 0;

			result = h2.getHandScore().getHandStrength().getHandStrength()
					- h1.getHandScore().getHandStrength().getHandStrength();

			if (result != 0) {
				return result;
			}

			result = h2.getHandScore().getHiHand().getiRankNbr() - h1.getHandScore().getHiHand().getiRankNbr();
			if (result != 0) {
				return result;
			}

			if ((h2.getHandScore().getLoHand() != null) && (h1.getHandScore().getLoHand() != null)) {
				result = h2.getHandScore().getLoHand().getiRankNbr() - h1.getHandScore().getLoHand().getiRankNbr();
			}

			if (result != 0) {
				return result;
			}

			if (h2.getHandScore().getKickers().size() > 0) {
				if (h1.getHandScore().getKickers().size() > 0) {
					result = h2.getHandScore().getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr()
							- h1.getHandScore().getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank()
									.getiRankNbr();
				}
				if (result != 0) {
					return result;
				}
			}

			if (h2.getHandScore().getKickers().size() > 1) {
				if (h1.getHandScore().getKickers().size() > 1) {
					result = h2.getHandScore().getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr()
							- h1.getHandScore().getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank()
									.getiRankNbr();
				}
				if (result != 0) {
					return result;
				}
			}

			if (h2.getHandScore().getKickers().size() > 2) {
				if (h1.getHandScore().getKickers().size() > 2) {
					result = h2.getHandScore().getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr()
							- h1.getHandScore().getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank()
									.getiRankNbr();
				}
				if (result != 0) {
					return result;
				}
			}

			if (h2.getHandScore().getKickers().size() > 3) {
				if (h1.getHandScore().getKickers().size() > 3) {
					result = h2.getHandScore().getKickers().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr()
							- h1.getHandScore().getKickers().get(eCardNo.FourthCard.getCardNo()).geteRank()
									.getiRankNbr();
				}
				if (result != 0) {
					return result;
				}
			}
			return 0;
		}
	};
}
