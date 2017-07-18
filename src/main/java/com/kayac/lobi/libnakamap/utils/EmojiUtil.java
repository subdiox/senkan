package com.kayac.lobi.libnakamap.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmojiUtil {
    private static final Pattern sEmoticonPattern = Pattern.compile("[[\\ue001-\\ue05a][\\ue101-\\ue15a][\\ue201-\\ue253][\\ue301-\\ue34d][\\ue401-\\ue44c][\\ue501-\\ue537]]");

    private EmojiUtil() {
    }

    public static final String getEmojiText(String text) {
        if (text == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        Matcher matcher = sEmoticonPattern.matcher(text);
        int index = 0;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            builder.append(text.subSequence(index, start));
            for (int i = start; i < end; i++) {
                builder.append(getEmoji(text.charAt(i)));
            }
            index = end;
        }
        builder.append(text.subSequence(index, text.length()));
        return builder.toString();
    }

    public static final String getEmoji(char args) {
        String s = args + "";
        switch (args) {
            case '':
                return new String(Character.toChars(128102));
            case '':
                return new String(Character.toChars(128103));
            case '':
                return new String(Character.toChars(128139));
            case '':
                return new String(Character.toChars(128104));
            case '':
                return new String(Character.toChars(128105));
            case '':
                return new String(Character.toChars(128085));
            case '':
                return new String(Character.toChars(128094));
            case '':
                return new String(Character.toChars(128247));
            case '':
                return new String(Character.toChars(128241));
            case '':
                return new String(Character.toChars(128224));
            case '':
                return new String(Character.toChars(128187));
            case '':
                return new String(Character.toChars(128074));
            case '':
                return new String(Character.toChars(128077));
            case '':
                return new String(Character.toChars(9994));
            case '':
                return new String(Character.toChars(9996));
            case '':
                return new String(Character.toChars(128587));
            case '':
                return new String(Character.toChars(127935));
            case '':
                return new String(Character.toChars(9971));
            case '':
                return new String(Character.toChars(127934));
            case '':
                return new String(Character.toChars(9918));
            case '':
                return new String(Character.toChars(127940));
            case '':
                return new String(Character.toChars(9917));
            case '':
                return new String(Character.toChars(128033));
            case '':
                return new String(Character.toChars(128052));
            case '':
                return new String(Character.toChars(128663));
            case '':
                return new String(Character.toChars(9973));
            case '':
                return new String(Character.toChars(9992));
            case '':
                return new String(Character.toChars(128643));
            case '':
                return new String(Character.toChars(128645));
            case '':
                return new String(Character.toChars(10067));
            case '':
                return new String(Character.toChars(10071));
            case '':
                return new String(Character.toChars(10084));
            case '':
                return new String(Character.toChars(128148));
            case '':
                return new String(Character.toChars(128336));
            case '':
                return new String(Character.toChars(128337));
            case '':
                return new String(Character.toChars(128338));
            case '':
                return new String(Character.toChars(128339));
            case '':
                return new String(Character.toChars(128340));
            case '':
                return new String(Character.toChars(128341));
            case '':
                return new String(Character.toChars(128342));
            case '':
                return new String(Character.toChars(128343));
            case '':
                return new String(Character.toChars(128344));
            case '':
                return new String(Character.toChars(128345));
            case '':
                return new String(Character.toChars(128346));
            case '':
                return new String(Character.toChars(128347));
            case '':
                return new String(Character.toChars(127800));
            case '':
                return new String(Character.toChars(128305));
            case '':
                return new String(Character.toChars(127801));
            case '':
                return new String(Character.toChars(127876));
            case '':
                return new String(Character.toChars(128141));
            case '':
                return new String(Character.toChars(128142));
            case '':
                return new String(Character.toChars(127968));
            case '':
                return new String(Character.toChars(9962));
            case '':
                return new String(Character.toChars(127970));
            case '':
                return new String(Character.toChars(128649));
            case '':
                return new String(Character.toChars(9981));
            case '':
                return new String(Character.toChars(128507));
            case '':
                return new String(Character.toChars(127908));
            case '':
                return new String(Character.toChars(127909));
            case '':
                return new String(Character.toChars(127925));
            case '':
                return new String(Character.toChars(128273));
            case '':
                return new String(Character.toChars(127927));
            case '':
                return new String(Character.toChars(127928));
            case '':
                return new String(Character.toChars(127930));
            case '':
                return new String(Character.toChars(127860));
            case '':
                return new String(Character.toChars(127864));
            case '':
                return new String(Character.toChars(127856));
            case '':
                return new String(Character.toChars(127866));
            case '':
                return new String(Character.toChars(9924));
            case '':
                return new String(Character.toChars(9729));
            case '':
                return new String(Character.toChars(9728));
            case '':
                return new String(Character.toChars(127764));
            case '':
                return new String(Character.toChars(127748));
            case '':
                return new String(Character.toChars(128124));
            case '':
                return new String(Character.toChars(128049));
            case '':
                return new String(Character.toChars(128047));
            case '':
                return new String(Character.toChars(128059));
            case '':
                return new String(Character.toChars(128041));
            case '':
                return new String(Character.toChars(128045));
            case '':
                return new String(Character.toChars(128051));
            case '':
                return new String(Character.toChars(128039));
            case '':
                return new String(Character.toChars(128523));
            case '':
                return new String(Character.toChars(128515));
            case '':
                return new String(Character.toChars(128542));
            case '':
                return new String(Character.toChars(128544));
            case '':
                return new String(Character.toChars(128169));
            case '':
                return new String(Character.toChars(128234));
            case '':
                return new String(Character.toChars(128238));
            case '':
                return new String(Character.toChars(9993));
            case '':
                return new String(Character.toChars(128242));
            case '':
                return new String(Character.toChars(128540));
            case '':
                return new String(Character.toChars(128525));
            case '':
                return new String(Character.toChars(128561));
            case '':
                return new String(Character.toChars(128531));
            case '':
                return new String(Character.toChars(128053));
            case '':
                return new String(Character.toChars(128025));
            case '':
                return new String(Character.toChars(128055));
            case '':
                return new String(Character.toChars(128125));
            case '':
                return new String(Character.toChars(128640));
            case '':
                return new String(Character.toChars(128081));
            case '':
                return new String(Character.toChars(128161));
            case '':
                return new String(Character.toChars(127808));
            case '':
                return new String(Character.toChars(128143));
            case '':
                return new String(Character.toChars(127873));
            case '':
                return new String(Character.toChars(128299));
            case '':
                return new String(Character.toChars(128269));
            case '':
                return new String(Character.toChars(127939));
            case '':
                return new String(Character.toChars(128296));
            case '':
                return new String(Character.toChars(127878));
            case '':
                return new String(Character.toChars(127809));
            case '':
                return new String(Character.toChars(127810));
            case '':
                return new String(Character.toChars(128127));
            case '':
                return new String(Character.toChars(128123));
            case '':
                return new String(Character.toChars(128128));
            case '':
                return new String(Character.toChars(128293));
            case '':
                return new String(Character.toChars(128188));
            case '':
                return new String(Character.toChars(128186));
            case '':
                return new String(Character.toChars(127828));
            case '':
                return new String(Character.toChars(9970));
            case '':
                return new String(Character.toChars(9978));
            case '':
                return new String(Character.toChars(9832));
            case '':
                return new String(Character.toChars(127905));
            case '':
                return new String(Character.toChars(127915));
            case '':
                return new String(Character.toChars(128191));
            case '':
                return new String(Character.toChars(128192));
            case '':
                return new String(Character.toChars(128251));
            case '':
                return new String(Character.toChars(128252));
            case '':
                return new String(Character.toChars(128250));
            case '':
                return new String(Character.toChars(128126));
            case '':
                return new String(Character.toChars(12349));
            case '':
                return new String(Character.toChars(126980));
            case '':
                return new String(Character.toChars(127386));
            case '':
                return new String(Character.toChars(128176));
            case '':
                return new String(Character.toChars(127919));
            case '':
                return new String(Character.toChars(127942));
            case '':
                return new String(Character.toChars(127937));
            case '':
                return new String(Character.toChars(127920));
            case '':
                return new String(Character.toChars(128014));
            case '':
                return new String(Character.toChars(128676));
            case '':
                return new String(Character.toChars(128690));
            case '':
                return new String(Character.toChars(128679));
            case '':
                return new String(Character.toChars(128697));
            case '':
                return new String(Character.toChars(128698));
            case '':
                return new String(Character.toChars(128700));
            case '':
                return new String(Character.toChars(128137));
            case '':
                return new String(Character.toChars(128164));
            case '':
                return new String(Character.toChars(9889));
            case '':
                return new String(Character.toChars(128096));
            case '':
                return new String(Character.toChars(128704));
            case '':
                return new String(Character.toChars(128701));
            case '':
                return new String(Character.toChars(128266));
            case '':
                return new String(Character.toChars(128226));
            case '':
                return new String(Character.toChars(127884));
            case '':
                return new String(Character.toChars(128274));
            case '':
                return new String(Character.toChars(128275));
            case '':
                return new String(Character.toChars(127750));
            case '':
                return new String(Character.toChars(127859));
            case '':
                return new String(Character.toChars(128211));
            case '':
                return new String(Character.toChars(128177));
            case '':
                return new String(Character.toChars(128185));
            case '':
                return new String(Character.toChars(128225));
            case '':
                return new String(Character.toChars(128170));
            case '':
                return new String(Character.toChars(127974));
            case '':
                return new String(Character.toChars(128677));
            case '':
                return new String(Character.toChars(127359));
            case '':
                return new String(Character.toChars(128655));
            case '':
                return new String(Character.toChars(128699));
            case '':
                return new String(Character.toChars(128110));
            case '':
                return new String(Character.toChars(127971));
            case '':
                return new String(Character.toChars(127975));
            case '':
                return new String(Character.toChars(127973));
            case '':
                return new String(Character.toChars(127978));
            case '':
                return new String(Character.toChars(127979));
            case '':
                return new String(Character.toChars(127976));
            case '':
                return new String(Character.toChars(128652));
            case '':
                return new String(Character.toChars(128661));
            case '':
                return new String(Character.toChars(128694));
            case '':
                return new String(Character.toChars(9875));
            case '':
                return new String(Character.toChars(127489));
            case '':
                return new String(Character.toChars(128159));
            case '':
                return new String(Character.toChars(10036));
            case '':
                return new String(Character.toChars(10035));
            case '':
                return new String(Character.toChars(128286));
            case '':
                return new String(Character.toChars(128685));
            case '':
                return new String(Character.toChars(128304));
            case '':
                return new String(Character.toChars(9855));
            case '':
                return new String(Character.toChars(128246));
            case '':
                return new String(Character.toChars(9829));
            case '':
                return new String(Character.toChars(9830));
            case '':
                return new String(Character.toChars(9824));
            case '':
                return new String(Character.toChars(9827));
            case '':
                return new String(Character.toChars(35));
            case '':
                return new String(Character.toChars(10175));
            case '':
                return new String(Character.toChars(127381));
            case '':
                return new String(Character.toChars(127385));
            case '':
                return new String(Character.toChars(127378));
            case '':
                return new String(Character.toChars(127542));
            case '':
                return new String(Character.toChars(127514));
            case '':
                return new String(Character.toChars(127543));
            case '':
                return new String(Character.toChars(127544));
            case '':
                return new String(Character.toChars(9898));
            case '':
                return new String(Character.toChars(128309));
            case '':
                return new String(Character.toChars(128307));
            case '':
                return new String(Character.toChars(49));
            case '':
                return new String(Character.toChars(50));
            case '':
                return new String(Character.toChars(51));
            case '':
                return new String(Character.toChars(52));
            case '':
                return new String(Character.toChars(53));
            case '':
                return new String(Character.toChars(54));
            case '':
                return new String(Character.toChars(55));
            case '':
                return new String(Character.toChars(56));
            case '':
                return new String(Character.toChars(57));
            case '':
                return new String(Character.toChars(48));
            case '':
                return new String(Character.toChars(127568));
            case '':
                return new String(Character.toChars(127545));
            case '':
                return new String(Character.toChars(127490));
            case '':
                return new String(Character.toChars(127380));
            case '':
                return new String(Character.toChars(127541));
            case '':
                return new String(Character.toChars(127539));
            case '':
                return new String(Character.toChars(127535));
            case '':
                return new String(Character.toChars(127546));
            case '':
                return new String(Character.toChars(128070));
            case '':
                return new String(Character.toChars(128071));
            case '':
                return new String(Character.toChars(128072));
            case '':
                return new String(Character.toChars(128073));
            case '':
                return new String(Character.toChars(11014));
            case '':
                return new String(Character.toChars(11015));
            case '':
                return new String(Character.toChars(10145));
            case '':
                return new String(Character.toChars(11013));
            case '':
                return new String(Character.toChars(8599));
            case '':
                return new String(Character.toChars(8598));
            case '':
                return new String(Character.toChars(8600));
            case '':
                return new String(Character.toChars(8601));
            case '':
                return new String(Character.toChars(9654));
            case '':
                return new String(Character.toChars(9664));
            case '':
                return new String(Character.toChars(9193));
            case '':
                return new String(Character.toChars(9194));
            case '':
                return new String(Character.toChars(128302));
            case '':
                return new String(Character.toChars(9800));
            case '':
                return new String(Character.toChars(9801));
            case '':
                return new String(Character.toChars(9802));
            case '':
                return new String(Character.toChars(9803));
            case '':
                return new String(Character.toChars(9804));
            case '':
                return new String(Character.toChars(9805));
            case '':
                return new String(Character.toChars(9806));
            case '':
                return new String(Character.toChars(9807));
            case '':
                return new String(Character.toChars(9808));
            case '':
                return new String(Character.toChars(9809));
            case '':
                return new String(Character.toChars(9810));
            case '':
                return new String(Character.toChars(9811));
            case '':
                return new String(Character.toChars(9934));
            case '':
                return new String(Character.toChars(128285));
            case '':
                return new String(Character.toChars(127383));
            case '':
                return new String(Character.toChars(169));
            case '':
                return new String(Character.toChars(174));
            case '':
                return new String(Character.toChars(128243));
            case '':
                return new String(Character.toChars(128244));
            case '':
                return new String(Character.toChars(9888));
            case '':
                return new String(Character.toChars(128129));
            case '':
                return new String(Character.toChars(128221));
            case '':
                return new String(Character.toChars(128084));
            case '':
                return new String(Character.toChars(127802));
            case '':
                return new String(Character.toChars(127799));
            case '':
                return new String(Character.toChars(127803));
            case '':
                return new String(Character.toChars(128144));
            case '':
                return new String(Character.toChars(127796));
            case '':
                return new String(Character.toChars(127797));
            case '':
                return new String(Character.toChars(128702));
            case '':
                return new String(Character.toChars(127911));
            case '':
                return new String(Character.toChars(127982));
            case '':
                return new String(Character.toChars(127867));
            case '':
                return new String(Character.toChars(12951));
            case '':
                return new String(Character.toChars(128684));
            case '':
                return new String(Character.toChars(128138));
            case '':
                return new String(Character.toChars(127880));
            case '':
                return new String(Character.toChars(128163));
            case '':
                return new String(Character.toChars(127881));
            case '':
                return new String(Character.toChars(9986));
            case '':
                return new String(Character.toChars(127872));
            case '':
                return new String(Character.toChars(12953));
            case '':
                return new String(Character.toChars(128189));
            case '':
                return new String(Character.toChars(128227));
            case '':
                return new String(Character.toChars(128082));
            case '':
                return new String(Character.toChars(128087));
            case '':
                return new String(Character.toChars(128097));
            case '':
                return new String(Character.toChars(128098));
            case '':
                return new String(Character.toChars(128132));
            case '':
                return new String(Character.toChars(128133));
            case '':
                return new String(Character.toChars(128134));
            case '':
                return new String(Character.toChars(128135));
            case '':
                return new String(Character.toChars(128136));
            case '':
                return new String(Character.toChars(128088));
            case '':
                return new String(Character.toChars(128089));
            case '':
                return new String(Character.toChars(128092));
            case '':
                return new String(Character.toChars(127916));
            case '':
                return new String(Character.toChars(128276));
            case '':
                return new String(Character.toChars(127926));
            case '':
                return new String(Character.toChars(128147));
            case '':
                return new String(Character.toChars(128151));
            case '':
                return new String(Character.toChars(128152));
            case '':
                return new String(Character.toChars(128153));
            case '':
                return new String(Character.toChars(128154));
            case '':
                return new String(Character.toChars(128155));
            case '':
                return new String(Character.toChars(128156));
            case '':
                return new String(Character.toChars(10024));
            case '':
                return new String(Character.toChars(11088));
            case '':
                return new String(Character.toChars(128168));
            case '':
                return new String(Character.toChars(128166));
            case '':
                return new String(Character.toChars(11093));
            case '':
                return new String(Character.toChars(10060));
            case '':
                return new String(Character.toChars(128162));
            case '':
                return new String(Character.toChars(127775));
            case '':
                return new String(Character.toChars(10068));
            case '':
                return new String(Character.toChars(10069));
            case '':
                return new String(Character.toChars(127861));
            case '':
                return new String(Character.toChars(127838));
            case '':
                return new String(Character.toChars(127846));
            case '':
                return new String(Character.toChars(127839));
            case '':
                return new String(Character.toChars(127841));
            case '':
                return new String(Character.toChars(127832));
            case '':
                return new String(Character.toChars(127834));
            case '':
                return new String(Character.toChars(127837));
            case '':
                return new String(Character.toChars(127836));
            case '':
                return new String(Character.toChars(127835));
            case '':
                return new String(Character.toChars(127833));
            case '':
                return new String(Character.toChars(127842));
            case '':
                return new String(Character.toChars(127843));
            case '':
                return new String(Character.toChars(127822));
            case '':
                return new String(Character.toChars(127818));
            case '':
                return new String(Character.toChars(127827));
            case '':
                return new String(Character.toChars(127817));
            case '':
                return new String(Character.toChars(127813));
            case '':
                return new String(Character.toChars(127814));
            case '':
                return new String(Character.toChars(127874));
            case '':
                return new String(Character.toChars(127857));
            case '':
                return new String(Character.toChars(127858));
            case '':
                return new String(Character.toChars(128549));
            case '':
                return new String(Character.toChars(128527));
            case '':
                return new String(Character.toChars(128553));
            case '':
                return new String(Character.toChars(128548));
            case '':
                return new String(Character.toChars(128521));
            case '':
                return new String(Character.toChars(128565));
            case '':
                return new String(Character.toChars(128534));
            case '':
                return new String(Character.toChars(128554));
            case '':
                return new String(Character.toChars(128069));
            case '':
                return new String(Character.toChars(128518));
            case '':
                return new String(Character.toChars(128552));
            case '':
                return new String(Character.toChars(128567));
            case '':
                return new String(Character.toChars(128563));
            case '':
                return new String(Character.toChars(128530));
            case '':
                return new String(Character.toChars(128560));
            case '':
                return new String(Character.toChars(128562));
            case '':
                return new String(Character.toChars(128557));
            case '':
                return new String(Character.toChars(128514));
            case '':
                return new String(Character.toChars(128546));
            case '':
                return new String(Character.toChars(9786));
            case '':
                return new String(Character.toChars(128517));
            case '':
                return new String(Character.toChars(128545));
            case '':
                return new String(Character.toChars(128538));
            case '':
                return new String(Character.toChars(128536));
            case '':
                return new String(Character.toChars(128064));
            case '':
                return new String(Character.toChars(128067));
            case '':
                return new String(Character.toChars(128066));
            case '':
                return new String(Character.toChars(128068));
            case '':
                return new String(Character.toChars(128591));
            case '':
                return new String(Character.toChars(128075));
            case '':
                return new String(Character.toChars(128079));
            case '':
                return new String(Character.toChars(128076));
            case '':
                return new String(Character.toChars(128078));
            case '':
                return new String(Character.toChars(128080));
            case '':
                return new String(Character.toChars(128581));
            case '':
                return new String(Character.toChars(128582));
            case '':
                return new String(Character.toChars(128145));
            case '':
                return new String(Character.toChars(128583));
            case '':
                return new String(Character.toChars(128588));
            case '':
                return new String(Character.toChars(128107));
            case '':
                return new String(Character.toChars(128111));
            case '':
                return new String(Character.toChars(127936));
            case '':
                return new String(Character.toChars(127944));
            case '':
                return new String(Character.toChars(127921));
            case '':
                return new String(Character.toChars(127946));
            case '':
                return new String(Character.toChars(128665));
            case '':
                return new String(Character.toChars(128666));
            case '':
                return new String(Character.toChars(128658));
            case '':
                return new String(Character.toChars(128657));
            case '':
                return new String(Character.toChars(128659));
            case '':
                return new String(Character.toChars(127906));
            case '':
                return new String(Character.toChars(128647));
            case '':
                return new String(Character.toChars(128644));
            case '':
                return new String(Character.toChars(127885));
            case '':
                return new String(Character.toChars(128157));
            case '':
                return new String(Character.toChars(127886));
            case '':
                return new String(Character.toChars(127891));
            case '':
                return new String(Character.toChars(127890));
            case '':
                return new String(Character.toChars(127887));
            case '':
                return new String(Character.toChars(127746));
            case '':
                return new String(Character.toChars(128146));
            case '':
                return new String(Character.toChars(127754));
            case '':
                return new String(Character.toChars(127847));
            case '':
                return new String(Character.toChars(127879));
            case '':
                return new String(Character.toChars(128026));
            case '':
                return new String(Character.toChars(127888));
            case '':
                return new String(Character.toChars(127744));
            case '':
                return new String(Character.toChars(127806));
            case '':
                return new String(Character.toChars(127875));
            case '':
                return new String(Character.toChars(127889));
            case '':
                return new String(Character.toChars(127811));
            case '':
                return new String(Character.toChars(127877));
            case '':
                return new String(Character.toChars(127749));
            case '':
                return new String(Character.toChars(127751));
            case '':
                return new String(Character.toChars(127747));
            case '':
                return new String(Character.toChars(127752));
            case '':
                return new String(Character.toChars(127977));
            case '':
                return new String(Character.toChars(127912));
            case '':
                return new String(Character.toChars(127913));
            case '':
                return new String(Character.toChars(127980));
            case '':
                return new String(Character.toChars(127983));
            case '':
                return new String(Character.toChars(127984));
            case '':
                return new String(Character.toChars(127910));
            case '':
                return new String(Character.toChars(127981));
            case '':
                return new String(Character.toChars(128508));
            case '':
                return new String(Character.toChars(127471));
            case '':
                return new String(Character.toChars(127482));
            case '':
                return new String(Character.toChars(127467));
            case '':
                return new String(Character.toChars(127465));
            case '':
                return new String(Character.toChars(127470));
            case '':
                return new String(Character.toChars(127468));
            case '':
                return new String(Character.toChars(127466));
            case '':
                return new String(Character.toChars(127479));
            case '':
                return new String(Character.toChars(127464));
            case '':
                return new String(Character.toChars(127472));
            case '':
                return new String(Character.toChars(128116));
            case '':
                return new String(Character.toChars(128117));
            case '':
                return new String(Character.toChars(128118));
            case '':
                return new String(Character.toChars(128119));
            case '':
                return new String(Character.toChars(128120));
            case '':
                return new String(Character.toChars(128509));
            case '':
                return new String(Character.toChars(128130));
            case '':
                return new String(Character.toChars(128131));
            case '':
                return new String(Character.toChars(128044));
            case '':
                return new String(Character.toChars(128038));
            case '':
                return new String(Character.toChars(128032));
            case '':
                return new String(Character.toChars(128036));
            case '':
                return new String(Character.toChars(128057));
            case '':
                return new String(Character.toChars(128027));
            case '':
                return new String(Character.toChars(128024));
            case '':
                return new String(Character.toChars(128040));
            case '':
                return new String(Character.toChars(128018));
            case '':
                return new String(Character.toChars(128017));
            case '':
                return new String(Character.toChars(128058));
            case '':
                return new String(Character.toChars(128046));
            case '':
                return new String(Character.toChars(128048));
            case '':
                return new String(Character.toChars(128013));
            case '':
                return new String(Character.toChars(128020));
            case '':
                return new String(Character.toChars(128023));
            case '':
                return new String(Character.toChars(128043));
            case '':
                return new String(Character.toChars(128056));
            case '':
                return new String(Character.toChars(127344));
            case '':
                return new String(Character.toChars(127345));
            case '':
                return new String(Character.toChars(127374));
            case '':
                return new String(Character.toChars(127358));
            case '':
                return new String(Character.toChars(128062));
            case '':
                return new String(Character.toChars(8482));
            default:
                return s;
        }
    }
}
