package xyz.aungpyaephyo.padc.myanmarattractions.events;

import java.util.List;

import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.AttractionVO;

/**
 * Created by aung on 7/9/16.
 */
public class DataEvent {

    public static class AttractionDataLoadedEvent {

        private String extraMessage;
        private List<AttractionVO> attractionList;

        public AttractionDataLoadedEvent(String extraMessage, List<AttractionVO> attractionList) {
            this.extraMessage = extraMessage;
            this.attractionList = attractionList;
        }

        public String getExtraMessage() {
            return extraMessage;
        }

        public List<AttractionVO> getAttractionList() {
            return attractionList;
        }
    }

    public static class DatePickedEvent {
        private String dateOfBrith;

        public DatePickedEvent(String dateOfBrith) {
            this.dateOfBrith = dateOfBrith;
        }

        public String getDateOfBrith() {
            return dateOfBrith;
        }
    }

    public static class RefreshUserLoginStatusEvent {

    }

    public static class AttractionLoadedEvent {

        private List<AttractionVO> attractionList;

        public AttractionLoadedEvent(List<AttractionVO> attractionList) {
            this.attractionList = attractionList;
        }

        public List<AttractionVO> getAttractionList() {
            return attractionList;
        }
    }
}
