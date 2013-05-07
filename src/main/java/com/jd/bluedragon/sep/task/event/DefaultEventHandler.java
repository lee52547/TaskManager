package com.jd.bluedragon.sep.task.event;

import com.jd.bluedragon.sep.task.event.EventHandler;
import com.jd.bluedragon.sep.task.event.EventType;
import com.jd.bluedragon.sep.task.event.listener.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 默认事件调度类
 * Created with IntelliJ IDEA.
 * User: lijiale
 * Date: 13-4-19
 * Time: 下午4:12
 * To change this template use File | Settings | File Templates.
 */
public class DefaultEventHandler implements EventHandler {
    private Map<Integer,List<Listener>>  listMap = new HashMap<Integer, List<Listener>>();

    @Override
    public void handle(Event et) {
        if(listMap.containsKey(et.getEvenType())) {
            for(Listener listener : listMap.get(et.getEvenType())) {
                listener.excute();
            }
        }
    }

    @Override
    public void register(Listener listener) {
        if(!listMap.containsKey(listener.getEventType())) {
            List<Listener> list = new ArrayList<Listener>();
            listMap.put(listener.getEventType(),list);
        }
        listMap.get(listener.getEventType()).add(listener);
    }
}
