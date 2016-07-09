package xyz.aungpyaephyo.padc.myanmarattractions.events;

/**
 * Created by aung on 7/9/16.
 */
public class DataEvent {

    public static class AttractionDataLoadedEvent {
        private String extraMessage;

        public AttractionDataLoadedEvent(String extraMessage) {
            this.extraMessage = extraMessage;
        }

        public String getExtraMessage() {
            return extraMessage;
        }
    }
}
