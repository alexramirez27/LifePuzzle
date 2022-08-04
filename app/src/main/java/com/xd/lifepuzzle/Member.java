package com.xd.lifepuzzle;

import java.util.ArrayList;
import java.util.List;

public class Member {
    public String name, relationship, imageUrl, uniqueID;
    public List<Long> completionTimes;

    // Picture format?

    // Audio Message format?

    // Video Message format?

    public Member(){

    }

    public Member(String name, String relationship, String imageUrl, String uniqueID) {
        this.name = name;
        this.relationship = relationship;
        this.imageUrl = imageUrl;
        this.uniqueID = uniqueID;
        this.completionTimes = new ArrayList<>();
    }

    public Member(String name, String relationship, String uniqueID, List<Long> completionTime) {
        this.name = name;
        this.relationship = relationship;
        // this.imageUrl = imageUrl;
        this.uniqueID = uniqueID;
        this.completionTimes = completionTime;
    }

    public void addCompletionTimes(Long time) {
        completionTimes.add(time);
    }
}
