package com.zra.kanban.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zra.kanban.entity.KanbanGoal;

public class TestKanbanDetailLogic {

    public static void main(String[] args) {
        
        
        KanbanGoal goal1 = new KanbanGoal("","121",new Date(), new Date(), 2,
                BigDecimal.valueOf(10), BigDecimal.valueOf(2), BigDecimal.valueOf(10), BigDecimal.valueOf(10));
        
        KanbanGoal goal2= new KanbanGoal("","121", new Date(), new Date(), 2,
                BigDecimal.valueOf(10), BigDecimal.valueOf(2), BigDecimal.valueOf(10), BigDecimal.valueOf(10));
        
        List<KanbanGoal> list = new ArrayList<KanbanGoal>();
        list.add(goal1);
        list.add(goal2);
        
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }
}
