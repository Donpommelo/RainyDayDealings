package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mygdx.game.RainyDayGame;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.stuff.Card;

public class CardActor extends ARDDActor {

	private PlayState ps;
	
	private Card card;
	
	public static final int cardWidth = 75;
	public static final int cardHeight = 105;
	
	public CardActor(PlayState ps, Card card) {
		super(0, 0, cardWidth, cardHeight);
		this.ps = ps;
		this.card = card;
		
		final PlayState playstate = ps;
		final CardActor me = this;
		
		addListener(new DragListener() {
			
			private boolean inHand = true;
			private int lastIndex;
			private int newX, newY;
			
			@Override
			public void drag(InputEvent event, float x, float y, int pointer) {
				
				newX = Gdx.input.getX() - cardWidth / 2;
				newY = Gdx.graphics.getHeight() - Gdx.input.getY() - cardHeight / 2;
				
				event.getListenerActor().setPosition(newX, newY);
				
				if (newY > cardHeight) {
					if (inHand) {
						inHand = false;
						lastIndex = playstate.getHandActor().removeCardFromHand(me);
					}
				} else {
					if (!inHand) {
						inHand = true;
						playstate.getHandActor().addCardToHand(lastIndex, me);
					}
					
				}
			}
			
			@Override
			public void dragStart(InputEvent event, float x, float y, int pointer) {
				playstate.setDragToScroll(false);
			}
			
			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer) {
				playstate.setDragToScroll(true);
				
				if (newY > cardHeight) {
					//Attempt to play the card
		//			me.card.onPlay(unit);
					
					if (!inHand) {
						inHand = true;
						playstate.getHandActor().addCardToHand(lastIndex, me);
					}
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
