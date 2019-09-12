package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mygdx.game.RainyDayGame;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Card;

/**
 * The CardActor represents a single card in the player's hand (and possibly in other locations too eventually?)
 * @author Zachary Tu
 *
 */
public class CardActor extends ARDDActor {

	public static final int cardWidth = 75;
	public static final int cardHeight = 105;
	public static final int handThreshold = 80;
	
	private PlayState ps;
	
	//This is the card that this actor represents
	private Card card;
	
	//This holds the card's location when it is being dragged by the mouse. This saves memory maybe idk
	public Vector2 tmpVec = new Vector2(0, 0);
	
	public CardActor(PlayState ps, final Card card) {
		super(0, 0, cardWidth, cardHeight);
		this.ps = ps;
		this.card = card;
		
		final PlayState playstate = ps;
		final CardActor me = this;
		
		addListener(new DragListener() {
			
			private boolean inHand = true;
			private int lastIndex;
			
			@Override
			public void drag(InputEvent event, float x, float y, int pointer) {
				
				//When dragging the card, its location is set to the mouse location, unprojected through camera to account for screen size changes.
				tmpVec.set(Gdx.input.getX(), Gdx.input.getY());
				RainyDayGame.viewportUI.unproject(tmpVec);
				
				event.getListenerActor().setPosition(tmpVec.x, tmpVec.y);
				
				//When dragging a card above the hand threshold, it is removed from hand and remember its index
				//When it is dragged beneath the threshold return it to hand. (maybe wait for it to be released?)
				if (tmpVec.y > handThreshold) {
					if (inHand) {
						inHand = false;
						lastIndex = playstate.getHandActor().removeCardFromHand(me.getCard());
					}
				} else {
					if (!inHand) {
						inHand = true;
						playstate.getHandActor().addCardToHand(lastIndex, me.getCard());
					}
				}
			}
			
			@Override
			public void dragStart(InputEvent event, float x, float y, int pointer) {
				
				//This tells the playstate to disable camera scrolling when a card is being dragged b/c that was really annoying.
				playstate.setDragToScroll(false);
			}
			
			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer) {
				playstate.setDragToScroll(true);
				
				//If card released above hand threshold, attempt to play it. Otherwise return it to hand.
				if (tmpVec.y > handThreshold) {
					playstate.getAm().playCard(playstate.getPm().getCurrentUnit(), card);
				} else {
					playstate.getHandActor().syncCardPositions();
				}
			}
			
		});
	}

	@Override
    public void draw(Batch batch, float alpha) {
         
//		batch.setProjectionMatrix(ps.sprite.combined);
		RainyDayGame.SYSTEM_FONT_SPRITE.draw(batch, card.getCardText(), getX(), getY() + cardHeight);
    }
	
	public Card getCard() {
		return card;
	}
}
