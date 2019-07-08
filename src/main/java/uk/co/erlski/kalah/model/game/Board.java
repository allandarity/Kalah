package uk.co.erlski.kalah.model.game;

import java.util.*;

public class Board {

    private HashMap<Long, Pit> pits = new HashMap<>();
    private List<Integer> topPits = Arrays.asList(2,3,4,5,6,7);
    private List<Integer> bottomPits = Arrays.asList(8,9,10,11,12,13);

    public Board() {}

    public void setupBoard() {
        for(Long i = 1L; i < 15; i++) {
            pits.put(i, new Pit(i));
        }
    }

    public Pit getOppositePit(final Long startingPit, final PlayerPosition playerPosition) {
        Long index = 0L;
        if(playerPosition.equals(PlayerPosition.TOP)){
            index = (long) topPits.indexOf(startingPit);
        } else {
            index = (long) bottomPits.indexOf(bottomPits);
        }
        return getPit(index);
    }

    public void clearBoard() {
        pits.clear();
    }

    public Pit getPit(final Long position) {
        return pits.get(position);
    }

    public Pit getHomePit(final PlayerPosition playerPosition) {
        return playerPosition.equals(PlayerPosition.TOP) ? getPit(1L) : getPit(14L);
    }

    public List<String> getPlayerPitsValues(final PlayerPosition playerPosition) {
        List<String> pitsContainer = new ArrayList<>();
        switch(playerPosition) {
            case TOP:
                for(Long i = 1L; i < 8; i++) {
                    pitsContainer.add(getPit(i).toString());
                }
                break;
            case BOTTOM:
                for(Long i = 8L; i < 15; i++) {
                    pitsContainer.add(getPit(i).toString());
                }
                break;
        }
        return pitsContainer;
    }

    @Override
    public String toString() {
        StringBuffer top = new StringBuffer();
        StringBuffer bottom = new StringBuffer();
        StringBuffer joined = new StringBuffer();
        pits.forEach((k, v) -> {
            if(k <= 7) {
                top.append(v);
            } else {
                bottom.append(v);
            }
        });

        joined.append(top);
        joined.append("----------");
        joined.append(bottom);

        return joined.toString();

    }



}
