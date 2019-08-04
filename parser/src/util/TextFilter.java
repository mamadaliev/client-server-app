package util;

/**
 * Text Filter.
 *
 */
public class TextFilter {

    /**
     * Filter a text.
     *
     * Filters:
     * }, -> &0001;
     *
     * @param text Text without filter.
     * @return Text with filter.
     */
    public static String filter(String text) {
        return text.replaceAll("},", "&0001;");
    }

    /**
     * Filter out a text.
     *
     * Filters:
     * }, <- &0001;
     *
     * @param text Text without filter.
     * @return Text with filter.
     */
    public static String filterOut(String text) {
        return text.replaceAll("&0001;", "},");
    }
}
