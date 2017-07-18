package com.kayac.lobi.sdk.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import com.coremedia.iso.boxes.apple.AppleWaveBox;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.value.StampStoreValue;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmoticonUtil {
    private static Map<String, Bitmap> sEmoticonMap;
    private static final Pattern sEmoticonPattern = Pattern.compile("[\\ue003\\ue006\\ue007\\ue008\\ue009\\ue00a\\ue00b\\ue00c\\ue00d\\ue00e\\ue010\\ue011\\ue012\\ue013\\ue014\\ue015\\ue016\\ue018\\ue019\\ue01a\\ue01b\\ue01c\\ue01d\\ue01e\\ue021\\ue022\\ue023\\ue02d\\ue030\\ue033\\ue034\\ue036\\ue038\\ue03a\\ue03b\\ue03c\\ue03d\\ue03e\\ue03f\\ue043\\ue044\\ue045\\ue046\\ue047\\ue048\\ue049\\ue04a\\ue04b\\ue04c\\ue04f\\ue052\\ue055\\ue056\\ue057\\ue058\\ue059\\ue103\\ue104\\ue105\\ue106\\ue107\\ue108\\ue10b\\ue10e\\ue10f\\ue110\\ue112\\ue114\\ue115\\ue118\\ue11f\\ue120\\ue123\\ue125\\ue126\\ue12a\\ue12f\\ue132\\ue136\\ue13c\\ue13d\\ue13e\\ue148\\ue14d\\ue14e\\ue14f\\ue151\\ue153\\ue154\\ue155\\ue156\\ue157\\ue158\\ue159\\ue202\\ue208\\ue20a\\ue20c\\ue20d\\ue20e\\ue20f\\ue210\\ue211\\ue212\\ue21c\\ue21d\\ue21e\\ue21f\\ue220\\ue221\\ue222\\ue223\\ue224\\ue225\\ue229\\ue22a\\ue22b\\ue236\\ue237\\ue238\\ue239\\ue23f\\ue240\\ue241\\ue242\\ue243\\ue244\\ue245\\ue246\\ue247\\ue248\\ue249\\ue24a\\ue24d\\ue24e\\ue24f\\ue252\\ue301\\ue304\\ue30a\\ue30b\\ue30e\\ue311\\ue313\\ue314\\ue315\\ue31c\\ue323\\ue324\\ue325\\ue326\\ue327\\ue32e\\ue330\\ue331\\ue334\\ue338\\ue339\\ue340\\ue342\\ue345\\ue34b\\ue402\\ue403\\ue404\\ue405\\ue406\\ue407\\ue40a\\ue40e\\ue411\\ue413\\ue415\\ue416\\ue419\\ue41b\\ue42a\\ue42e\\ue434\\ue435\\ue43c\\ue43e\\ue443\\ue44b\\ue502\\ue503\\ue523\\ue536\\ue537]");

    private static void makeEmoticonMap(Context context) {
        if (sEmoticonMap == null) {
            Map<String, String> map = new HashMap<String, String>() {
                {
                    put(String.valueOf(Character.toChars(57347)), "kissmark");
                    put(String.valueOf(Character.toChars(57350)), "t_shirt");
                    put(String.valueOf(Character.toChars(57351)), "shoe");
                    put(String.valueOf(Character.toChars(57352)), "camera");
                    put(String.valueOf(Character.toChars(57353)), "telephone");
                    put(String.valueOf(Character.toChars(57354)), "mobilephone");
                    put(String.valueOf(Character.toChars(57355)), "faxto");
                    put(String.valueOf(Character.toChars(57356)), "pc");
                    put(String.valueOf(Character.toChars(57357)), "punch");
                    put(String.valueOf(Character.toChars(57358)), "good");
                    put(String.valueOf(Character.toChars(57360)), "rock");
                    put(String.valueOf(Character.toChars(57361)), "scissors");
                    put(String.valueOf(Character.toChars(57362)), "paper");
                    put(String.valueOf(Character.toChars(57363)), "ski");
                    put(String.valueOf(Character.toChars(57364)), "golf");
                    put(String.valueOf(Character.toChars(57365)), "tennis");
                    put(String.valueOf(Character.toChars(57366)), "baseball");
                    put(String.valueOf(Character.toChars(57368)), "soccer");
                    put(String.valueOf(Character.toChars(57369)), "fish");
                    put(String.valueOf(Character.toChars(57370)), "horse");
                    put(String.valueOf(Character.toChars(57371)), "car");
                    put(String.valueOf(Character.toChars(57372)), "yacht");
                    put(String.valueOf(Character.toChars(57373)), "airplane");
                    put(String.valueOf(Character.toChars(57374)), "train");
                    put(String.valueOf(Character.toChars(57377)), "sign01");
                    put(String.valueOf(Character.toChars(57378)), "heart01");
                    put(String.valueOf(Character.toChars(57379)), "heart03");
                    put(String.valueOf(Character.toChars(57389)), "clock");
                    put(String.valueOf(Character.toChars(57392)), "cherryblossom");
                    put(String.valueOf(Character.toChars(57395)), "xmas");
                    put(String.valueOf(Character.toChars(57396)), "ring");
                    put(String.valueOf(Character.toChars(57398)), "house");
                    put(String.valueOf(Character.toChars(57400)), "building");
                    put(String.valueOf(Character.toChars(57402)), "gasstation");
                    put(String.valueOf(Character.toChars(57403)), "fuji");
                    put(String.valueOf(Character.toChars(57404)), "karaoke");
                    put(String.valueOf(Character.toChars(57405)), "movie");
                    put(String.valueOf(Character.toChars(57406)), "note");
                    put(String.valueOf(Character.toChars(57407)), "key");
                    put(String.valueOf(Character.toChars(57411)), "restaurant");
                    put(String.valueOf(Character.toChars(57412)), "wine");
                    put(String.valueOf(Character.toChars(57413)), "cafe");
                    put(String.valueOf(Character.toChars(57414)), "cake");
                    put(String.valueOf(Character.toChars(57415)), "beer");
                    put(String.valueOf(Character.toChars(57416)), "snow");
                    put(String.valueOf(Character.toChars(57417)), "cloud");
                    put(String.valueOf(Character.toChars(57418)), "sun");
                    put(String.valueOf(Character.toChars(57419)), "rain");
                    put(String.valueOf(Character.toChars(57420)), "moon3");
                    put(String.valueOf(Character.toChars(57423)), "cat");
                    put(String.valueOf(Character.toChars(57426)), "dog");
                    put(String.valueOf(Character.toChars(57429)), "penguin");
                    put(String.valueOf(Character.toChars(57430)), "delicious");
                    put(String.valueOf(Character.toChars(57431)), "happy01");
                    put(String.valueOf(Character.toChars(57432)), "despair");
                    put(String.valueOf(Character.toChars(57433)), "angry");
                    put(String.valueOf(Character.toChars(57603)), "loveletter");
                    put(String.valueOf(Character.toChars(57604)), "phoneto");
                    put(String.valueOf(Character.toChars(57605)), "bleah");
                    put(String.valueOf(Character.toChars(57606)), "lovely");
                    put(String.valueOf(Character.toChars(57607)), "shock");
                    put(String.valueOf(Character.toChars(57608)), "coldsweats02");
                    put(String.valueOf(Character.toChars(57611)), "pig");
                    put(String.valueOf(Character.toChars(57614)), "crown");
                    put(String.valueOf(Character.toChars(57615)), "flair");
                    put(String.valueOf(Character.toChars(57616)), "clover");
                    put(String.valueOf(Character.toChars(57618)), "present");
                    put(String.valueOf(Character.toChars(57620)), "search");
                    put(String.valueOf(Character.toChars(57621)), "run");
                    put(String.valueOf(Character.toChars(57624)), "maple");
                    put(String.valueOf(Character.toChars(57631)), "chair");
                    put(String.valueOf(Character.toChars(57632)), "fastfood");
                    put(String.valueOf(Character.toChars(57635)), "spa");
                    put(String.valueOf(Character.toChars(57637)), "ticket");
                    put(String.valueOf(Character.toChars(57638)), "cd");
                    put(String.valueOf(Character.toChars(57642)), "tv");
                    put(String.valueOf(Character.toChars(57647)), "dollar");
                    put(String.valueOf(Character.toChars(57650)), "motorsports");
                    put(String.valueOf(Character.toChars(57654)), "bicycle");
                    put(String.valueOf(Character.toChars(57660)), "sleepy");
                    put(String.valueOf(Character.toChars(57661)), "thunder");
                    put(String.valueOf(Character.toChars(57662)), "boutique");
                    put(String.valueOf(Character.toChars(57672)), "book");
                    put(String.valueOf(Character.toChars(57677)), "bank");
                    put(String.valueOf(Character.toChars(57678)), "signaler");
                    put(String.valueOf(Character.toChars(57679)), "parking");
                    put(String.valueOf(Character.toChars(57681)), "toilet");
                    put(String.valueOf(Character.toChars(57683)), "postoffice");
                    put(String.valueOf(Character.toChars(57684)), "atm");
                    put(String.valueOf(Character.toChars(57685)), "hospital");
                    put(String.valueOf(Character.toChars(57686)), "24hours");
                    put(String.valueOf(Character.toChars(57687)), "school");
                    put(String.valueOf(Character.toChars(57688)), "hotel");
                    put(String.valueOf(Character.toChars(57689)), "bus");
                    put(String.valueOf(Character.toChars(57858)), "ship");
                    put(String.valueOf(Character.toChars(57864)), "nosmoking");
                    put(String.valueOf(Character.toChars(57866)), "wheelchair");
                    put(String.valueOf(Character.toChars(57868)), "heart");
                    put(String.valueOf(Character.toChars(57869)), "diamond");
                    put(String.valueOf(Character.toChars(57870)), "spade");
                    put(String.valueOf(Character.toChars(57871)), "club");
                    put(String.valueOf(Character.toChars(57872)), "sharp");
                    put(String.valueOf(Character.toChars(57873)), "freedial");
                    put(String.valueOf(Character.toChars(57874)), StampStoreValue.NEW);
                    put(String.valueOf(Character.toChars(57884)), "one");
                    put(String.valueOf(Character.toChars(57885)), "two");
                    put(String.valueOf(Character.toChars(57886)), "three");
                    put(String.valueOf(Character.toChars(57887)), "four");
                    put(String.valueOf(Character.toChars(57888)), "five");
                    put(String.valueOf(Character.toChars(57889)), "six");
                    put(String.valueOf(Character.toChars(57890)), "seven");
                    put(String.valueOf(Character.toChars(57891)), "eight");
                    put(String.valueOf(Character.toChars(57892)), "nine");
                    put(String.valueOf(Character.toChars(57893)), "zero");
                    put(String.valueOf(Character.toChars(57897)), "id");
                    put(String.valueOf(Character.toChars(57898)), "full");
                    put(String.valueOf(Character.toChars(57899)), "empty");
                    put(String.valueOf(Character.toChars(57910)), "up");
                    put(String.valueOf(Character.toChars(57911)), "upwardleft");
                    put(String.valueOf(Character.toChars(57912)), "downwardright");
                    put(String.valueOf(Character.toChars(57913)), "downwardleft");
                    put(String.valueOf(Character.toChars(57919)), "aries");
                    put(String.valueOf(Character.toChars(57920)), "taurus");
                    put(String.valueOf(Character.toChars(57921)), "gemini");
                    put(String.valueOf(Character.toChars(57922)), "cancer");
                    put(String.valueOf(Character.toChars(57923)), "leo");
                    put(String.valueOf(Character.toChars(57924)), "virgo");
                    put(String.valueOf(Character.toChars(57925)), "libra");
                    put(String.valueOf(Character.toChars(57926)), "scorpius");
                    put(String.valueOf(Character.toChars(57927)), "sagittarius");
                    put(String.valueOf(Character.toChars(57928)), "capricornus");
                    put(String.valueOf(Character.toChars(57929)), "aquarius");
                    put(String.valueOf(Character.toChars(57930)), "pisces");
                    put(String.valueOf(Character.toChars(57933)), "ok");
                    put(String.valueOf(Character.toChars(57934)), "copyright");
                    put(String.valueOf(Character.toChars(57935)), "r_mark");
                    put(String.valueOf(Character.toChars(57938)), "danger");
                    put(String.valueOf(Character.toChars(58113)), "memo");
                    put(String.valueOf(Character.toChars(58116)), "tulip");
                    put(String.valueOf(Character.toChars(58122)), "music");
                    put(String.valueOf(Character.toChars(58123)), "bottle");
                    put(String.valueOf(Character.toChars(58126)), "smoking");
                    put(String.valueOf(Character.toChars(58129)), "bomb");
                    put(String.valueOf(Character.toChars(58131)), "hairsalon");
                    put(String.valueOf(Character.toChars(58132)), "ribbon");
                    put(String.valueOf(Character.toChars(58133)), "secret");
                    put(String.valueOf(Character.toChars(58140)), "rouge");
                    put(String.valueOf(Character.toChars(58147)), "bag");
                    put(String.valueOf(Character.toChars(58148)), "slate");
                    put(String.valueOf(Character.toChars(58149)), "bell");
                    put(String.valueOf(Character.toChars(58150)), "notes");
                    put(String.valueOf(Character.toChars(58151)), "heart04");
                    put(String.valueOf(Character.toChars(58158)), "shine");
                    put(String.valueOf(Character.toChars(58160)), "dash");
                    put(String.valueOf(Character.toChars(58161)), "sweat02");
                    put(String.valueOf(Character.toChars(58164)), "annoy");
                    put(String.valueOf(Character.toChars(58168)), "japanesetea");
                    put(String.valueOf(Character.toChars(58169)), "bread");
                    put(String.valueOf(Character.toChars(58176)), "noodle");
                    put(String.valueOf(Character.toChars(58178)), "riceball");
                    put(String.valueOf(Character.toChars(58181)), "apple");
                    put(String.valueOf(Character.toChars(58187)), "birthday");
                    put(String.valueOf(Character.toChars(58370)), "catface");
                    put(String.valueOf(Character.toChars(58371)), "think");
                    put(String.valueOf(Character.toChars(58372)), "smile");
                    put(String.valueOf(Character.toChars(58373)), "wink");
                    put(String.valueOf(Character.toChars(58374)), "wobbly");
                    put(String.valueOf(Character.toChars(58375)), "sad");
                    put(String.valueOf(Character.toChars(58378)), "confident");
                    put(String.valueOf(Character.toChars(58382)), "gawk");
                    put(String.valueOf(Character.toChars(58385)), "crying");
                    put(String.valueOf(Character.toChars(58387)), "weep");
                    put(String.valueOf(Character.toChars(58389)), "coldsweats01");
                    put(String.valueOf(Character.toChars(58390)), "pout");
                    put(String.valueOf(Character.toChars(58393)), "eye");
                    put(String.valueOf(Character.toChars(58395)), "ear");
                    put(String.valueOf(Character.toChars(58410)), "basketball");
                    put(String.valueOf(Character.toChars(58414)), "rvcar");
                    put(String.valueOf(Character.toChars(58420)), "subway");
                    put(String.valueOf(Character.toChars(58421)), "bullettrain");
                    put(String.valueOf(Character.toChars(58428)), "sprinkle");
                    put(String.valueOf(Character.toChars(58430)), AppleWaveBox.TYPE);
                    put(String.valueOf(Character.toChars(58435)), "typhoon");
                    put(String.valueOf(Character.toChars(58443)), "night");
                    put(String.valueOf(Character.toChars(58626)), "art");
                    put(String.valueOf(Character.toChars(58627)), "drama");
                    put(String.valueOf(Character.toChars(58659)), "chick");
                    put(String.valueOf(Character.toChars(58678)), "foot");
                    put(String.valueOf(Character.toChars(58679)), "tm");
                }
            };
            sEmoticonMap = new HashMap();
            Resources res = context.getResources();
            if (res.getDisplayMetrics().densityDpi <= 160) {
                Options options = new Options();
                options.inDensity = 320;
                options.inTargetDensity = 160;
            }
            Rect rect = new Rect();
            for (Entry<String, String> entry : map.entrySet()) {
                sEmoticonMap.put((String) entry.getKey(), BitmapFactory.decodeResource(res, res.getIdentifier("lobi_typepad_emoticon_" + ((String) entry.getValue()), "drawable", context.getPackageName())));
            }
            map.clear();
        }
    }

    public static Spannable getEmoticonSpannedText(Context context, CharSequence text) {
        if (text == null) {
            return null;
        }
        makeEmoticonMap(context);
        Spannable builder = new SpannableStringBuilder();
        Matcher matcher = sEmoticonPattern.matcher(text);
        int index = 0;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            builder.append(text.subSequence(index, start));
            for (int i = start; i < end; i++) {
                String key = text.charAt(i) + "";
                Log.v("nakamap-sdk", "asserts: " + key);
                Bitmap bitmap = (Bitmap) sEmoticonMap.get(key);
                builder.append(key);
                builder.setSpan(new ImageSpan(context, bitmap), i, i + 1, 33);
            }
            index = end;
        }
        builder.append(text.subSequence(index, text.length()));
        return builder;
    }
}
