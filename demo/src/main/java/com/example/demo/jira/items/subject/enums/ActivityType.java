package com.example.demo.jira.items.subject.enums;

import java.util.ArrayList;
import java.util.List;

public enum ActivityType{
    SEMINAR{
        @Override
        public List<String> resolveGroups(List<String> groups){
            return groups;
        }

    },
    LABORATORY{
        @Override
        public List<String> resolveGroups(List<String> groups){
            List<String> groupsToReturn = new ArrayList<>();
            for(String group : groups){
                groupsToReturn.add(group+"A");
                groupsToReturn.add(group+"B");
            }
            return groupsToReturn;
        }
    },
    LECTURE{
      @Override
      public List<String> resolveGroups(List<String> groups){
          StringBuilder common = new StringBuilder();
          for(String group : groups){
              common.append(group);
          }
          return List.of(new String(common));
      }
    };

    public abstract List<String> resolveGroups(List<String> groups);
}
