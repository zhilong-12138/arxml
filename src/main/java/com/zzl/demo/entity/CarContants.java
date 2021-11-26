package com.zzl.demo.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 在售商品数据状态常量
 *
 * @author zhiLong
 * @version 1.0
 * @date 2021/3/29 11:40
 */
public final class CarContants {
    public final static Map<String, String> CODE_MAPS = new HashMap<String, String>();

    public static final String NIGHT = "night";
    public static final String NIGHTS = "nights";

    public static final String HEADLIGHT = "headlight";
    public static final String HEADLIGHTS = "headlights";

    public static final String WINDOW = "window";
    public static final String WINDOWS = "windows";

    public static final String BONNET = "bonnet";
    public static final String BONNETS = "bonnets";

    public static final String LUGGAGE = "luggage";
    public static final String LUGGAGES = "luggages";

    public static final String DOOR = "door";
    public static final String DOORS = "doors";

    public static final String GULL = "gull";
    public static final String GULLS = "gulls";

    public static final String ROOF = "roof";
    public static final String ROOFS = "roofs";

    public static final String LOWDOWN = "lowdown";
    public static final String LOWDOWNS = "lowdowns";

    public static final String AERO = "aero";
    public static final String AEROS = "aeros";

    public static final String NEON = "neon";
    public static final String NEONS = "neons";

    public static final String RUN = "run";
    public static final String RUNS = "runs";

    public static final String ZOOM = "zoom";
    public static final String ZOOMS = "zooms";

    public static final String RIDE = "ride";
    public static final String RIDES = "rides";



    static {
        CODE_MAPS.put(NIGHT, "晚上了");
        CODE_MAPS.put(NIGHTS, "白天了");

        CODE_MAPS.put(HEADLIGHT, "大灯开了");
        CODE_MAPS.put(HEADLIGHTS, "大灯关了");

        CODE_MAPS.put(WINDOW, "窗户开了");
        CODE_MAPS.put(WINDOWS, "窗户关了");

        CODE_MAPS.put(BONNET, "引擎盖开了");
        CODE_MAPS.put(BONNETS, "引擎盖关了");
    }
}
