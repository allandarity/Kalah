package uk.co.erlski.kalah.model.board;

import uk.co.erlski.kalah.model.enums.PlayerPosition;

import java.util.*;

public class Board {

    private HashMap<Long, Pit> pits = new HashMap<>();
    private List<Long> topPits = Arrays.asList(2L,3L,4L,5L,6L,7L);
    private List<Long> bottomPits = Arrays.asList(8L,9L,10L,11L,12L,13L);

    public Board() {}

    public void setupBoard() {
        for(Long i = 1L; i < 15; i++) {
            pits.put(i, new Pit(i));
        }
    }

    public void clearBoard() {
        pits.clear();
    }

    //TODO: validation
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
