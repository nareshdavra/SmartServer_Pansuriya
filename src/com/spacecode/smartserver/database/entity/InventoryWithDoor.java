package com.spacecode.smartserver.database.entity;

/**
 * Created by MY on 13/08/2015.
 */


import com.spacecode.sdk.SmartLogger;
import com.spacecode.sdk.XmlObject;
import com.spacecode.sdk.device.data.DoorInfo;
import com.spacecode.sdk.device.data.Inventory;
import com.spacecode.sdk.user.data.AccessType;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.spacecode.sdk.SmartLogger;
import com.spacecode.sdk.XmlObject;
import com.spacecode.sdk.user.data.AccessType;
import com.spacecode.smartserver.database.dao.DaoAccessType;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.DatatypeConverter;


public final class InventoryWithDoor {
    private int _id = 0;
    private List<String> _tagsAll = new ArrayList();
    private List<String> _tagsPresent = new ArrayList();
    private List<String> _tagsAdded = new ArrayList();
    private List<String> _tagsRemoved = new ArrayList();
    private Date _creationDate = new Date();
    private String _username = "";
    private DoorInfo _doorused = DoorInfo.DI_NO_DOOR;
    private AccessType _accessType;
    static final String NODE_ID = "id";
    static final String NODE_DATE = "date";
    static final String NODE_ADDED_TAGS = "addedTags";
    static final String NODE_PRESENT_TAGS = "presentTags";
    static final String NODE_REMOVED_TAGS = "removedTags";
    static final String NODE_TAG_UID = "tag";
    static final String NODE_USERNAME = "username";
    static final String NODE_ACCESS_TYPE = "accessType";
    private static final Pattern TAG_PATTERN = Pattern.compile("<tag>(.*?)</tag>");

    public InventoryWithDoor(InventoryWithDoor copyInventory) {
        this._accessType = AccessType.UNDEFINED;
        this._tagsAll = new ArrayList(copyInventory.getTagsAll());
        this._tagsPresent = new ArrayList(copyInventory.getTagsPresent());
        this._tagsAdded = new ArrayList(copyInventory.getTagsAdded());
        this._tagsRemoved = new ArrayList(copyInventory.getTagsRemoved());
        this._creationDate = copyInventory._creationDate;
    }
    public InventoryWithDoor(Inventory copyInventory) {
        this._accessType = AccessType.UNDEFINED;
        this._tagsAll = new ArrayList(copyInventory.getTagsAll());
        this._tagsPresent = new ArrayList(copyInventory.getTagsPresent());
        this._tagsAdded = new ArrayList(copyInventory.getTagsAdded());
        this._tagsRemoved = new ArrayList(copyInventory.getTagsRemoved());
        this._creationDate = copyInventory.getCreationDate();
    }

    public InventoryWithDoor() {
        this._accessType = AccessType.UNDEFINED;
    }

    /** @deprecated */
    @Deprecated
    public InventoryWithDoor(int id, List<String> tagsAdded, List<String> tagsPresent, List<String> tagsRemoved, String initiatingUser, AccessType accessType, Date createdAt) {
        this._accessType = AccessType.UNDEFINED;
        this._id = id;
        this._tagsAdded = (List)(tagsAdded == null?new ArrayList():tagsAdded);
        this._tagsPresent = (List)(tagsPresent == null?new ArrayList():tagsPresent);
        this._tagsRemoved = (List)(tagsRemoved == null?new ArrayList():tagsRemoved);
        this._tagsAll = new ArrayList(this._tagsAdded);
        this._tagsAll.addAll(this._tagsPresent);
        this._username = initiatingUser == null?"":initiatingUser;
        this._accessType = accessType == null?AccessType.UNDEFINED:accessType;
        this._creationDate = createdAt == null?new Date():new Date(createdAt.getTime());
    }

    /** @deprecated */
    @Deprecated
    public int getId() {
        return this._id;
    }

    public List<String> getTagsAll() {
        return new ArrayList(this._tagsAll);
    }

    public List<String> getTagsPresent() {
        return new ArrayList(this._tagsPresent);
    }

    public List<String> getTagsAdded() {
        return new ArrayList(this._tagsAdded);
    }

    public List<String> getTagsRemoved() {
        return new ArrayList(this._tagsRemoved);
    }

    public Date getCreationDate() {
        return new Date(this._creationDate.getTime());
    }

    public static InventoryWithDoor generateLastInventory(InventoryWithDoor previousInventory, List<String> tagsList) {
        InventoryWithDoor newCurrentInventory = new InventoryWithDoor();
        newCurrentInventory._tagsAll.addAll(tagsList);
        Iterator i$ = tagsList.iterator();

        String tagUID;
        while(i$.hasNext()) {
            tagUID = (String)i$.next();
            if(previousInventory._tagsAll.contains(tagUID)) {
                newCurrentInventory._tagsPresent.add(tagUID);
            } else {
                newCurrentInventory._tagsAdded.add(tagUID);
            }
        }

        i$ = previousInventory._tagsAll.iterator();

        while(i$.hasNext()) {
            tagUID = (String)i$.next();
            if(!tagsList.contains(tagUID)) {
                newCurrentInventory._tagsRemoved.add(tagUID);
            }
        }

        return newCurrentInventory;
    }

    public int getNumberTotal() {
        return this._tagsAll.size();
    }

    public int getNumberAdded() {
        return this._tagsAdded.size();
    }

    public int getNumberRemoved() {
        return this._tagsRemoved.size();
    }

    public int getNumberPresent() {
        return this._tagsPresent.size();
    }

    public void setInitiatingUserName(String userName) {
        this._username = userName;
    }

    public String getUsername() {
        return this._username;
    }

    public AccessType getAccessType() {
        return this._accessType;
    }

    public void setAccessType(AccessType accessType) {
        this._accessType = accessType;
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append("<inventory>");
        sb.append("<").append("id").append(">");
        sb.append(this._id);
        sb.append("</").append("id").append(">");
        sb.append("<").append("date").append(">");
        sb.append(this._creationDate.getTime() / 1000L);
        sb.append("</").append("date").append(">");
        sb.append("<").append("presentTags").append(">");
        Iterator uee = this._tagsPresent.iterator();

        String tagUID;
        while(uee.hasNext()) {
            tagUID = (String)uee.next();
            sb.append("<").append("tag").append(">");
            sb.append(tagUID);
            sb.append("</").append("tag").append(">");
        }

        sb.append("</").append("presentTags").append(">");
        sb.append("<").append("addedTags").append(">");
        uee = this._tagsAdded.iterator();

        while(uee.hasNext()) {
            tagUID = (String)uee.next();
            sb.append("<").append("tag").append(">");
            sb.append(tagUID);
            sb.append("</").append("tag").append(">");
        }

        sb.append("</").append("addedTags").append(">");
        sb.append("<").append("removedTags").append(">");
        uee = this._tagsRemoved.iterator();

        while(uee.hasNext()) {
            tagUID = (String)uee.next();
            sb.append("<").append("tag").append(">");
            sb.append(tagUID);
            sb.append("</").append("tag").append(">");
        }


        sb.append("</").append("removedTags").append(">");
        if(this._username != null && !"".equals(this._username.trim())) {
            sb.append("<").append("username").append(">");
            sb.append(this._username);
            sb.append("</").append("username").append(">");
            sb.append("<").append("accessType").append(">");
            sb.append(this._accessType);
            sb.append("</").append("accessType").append(">");
            sb.append("<").append("userDoor").append(">");
            sb.append(this._doorused);
            sb.append("</").append("userDoor").append(">");
        }

        sb.append("</inventory>");

        try {
            return DatatypeConverter.printBase64Binary(sb.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException var4) {
            SmartLogger.getLogger().log(Level.SEVERE, "Unable to encode serialized inventory with UTF-8 charset.", var4);
            return "";
        }
    }

    public static InventoryWithDoor deserialize(String serializedInventory) {
        String decodedSerializedInventory;
        try {
            decodedSerializedInventory = new String(DatatypeConverter.parseBase64Binary(serializedInventory), "UTF-8");
        } catch (UnsupportedEncodingException var27) {
            SmartLogger.getLogger().log(Level.SEVERE, "Unable to decode serialized inventory with UTF-8 charset.", var27);
            return null;
        }

        int idStart = decodedSerializedInventory.indexOf("<id>");
        int idEnd = decodedSerializedInventory.indexOf("</id>");
        int dateStart = decodedSerializedInventory.indexOf("<date>");
        int dateEnd = decodedSerializedInventory.indexOf("</date>");
        int presentListStart = decodedSerializedInventory.indexOf("<presentTags>");
        int presentListEnd = decodedSerializedInventory.indexOf("</presentTags>");
        int addedListStart = decodedSerializedInventory.indexOf("<addedTags>");
        int addedListEnd = decodedSerializedInventory.indexOf("</addedTags>");
        int removedListStart = decodedSerializedInventory.indexOf("<removedTags>");
        int removedListEnd = decodedSerializedInventory.indexOf("</removedTags>");
        if(!XmlObject.notNegativeIndex(new int[]{idStart, idEnd, dateStart, dateEnd, presentListStart, presentListEnd, addedListStart, addedListEnd, removedListStart, removedListEnd})) {
            return null;
        } else {
            InventoryWithDoor deserializedInventory = new InventoryWithDoor();
            String idXml = decodedSerializedInventory.substring(idStart + "<id>".length(), idEnd);

            try {
                deserializedInventory._id = Integer.parseInt(idXml);
            } catch (NumberFormatException var26) {
                SmartLogger.getLogger().log(Level.SEVERE, "Invalid ID ~ serialized inventory.", var26);
                return null;
            }

            String timestampXml = decodedSerializedInventory.substring(dateStart + "<date>".length(), dateEnd);

            try {
                deserializedInventory._creationDate = new Date(Long.parseLong(timestampXml) * 1000L);
            } catch (NumberFormatException var25) {
                SmartLogger.getLogger().log(Level.SEVERE, "Invalid timestamp ~ serialized inventory.", var25);
                return null;
            }

            String presentTagsList = decodedSerializedInventory.substring(presentListStart + "<presentTags>".length(), presentListEnd);
            Matcher matcher = TAG_PATTERN.matcher(presentTagsList);

            while(matcher.find()) {
                deserializedInventory._tagsPresent.add(matcher.group(1));
            }

            String addedTagsList = decodedSerializedInventory.substring(addedListStart + "<addedTags>".length(), addedListEnd);
            matcher = TAG_PATTERN.matcher(addedTagsList);

            while(matcher.find()) {
                deserializedInventory._tagsAdded.add(matcher.group(1));
            }

            String removedTagsList = decodedSerializedInventory.substring(removedListStart + "<removedTags>".length(), removedListEnd);
            matcher = TAG_PATTERN.matcher(removedTagsList);

            while(matcher.find()) {
                deserializedInventory._tagsRemoved.add(matcher.group(1));
            }

            deserializedInventory._tagsAll.addAll(deserializedInventory._tagsPresent);
            Iterator userIdStart = deserializedInventory._tagsAdded.iterator();

            while(userIdStart.hasNext()) {
                String userIdEnd = (String)userIdStart.next();
                if(!deserializedInventory._tagsAll.contains(userIdEnd)) {
                    deserializedInventory._tagsAll.add(userIdEnd);
                }
            }

            int userIdStart1 = decodedSerializedInventory.indexOf("<username>");
            if(userIdStart1 != -1) {
                int userIdEnd1 = decodedSerializedInventory.indexOf("</username>");
                int accessTypeStart = decodedSerializedInventory.indexOf("<accessType>");
                int accessTypeEnd = decodedSerializedInventory.indexOf("</accessType>");
                if(userIdEnd1 != -1 && accessTypeEnd != -1 && accessTypeStart != -1) {
                    deserializedInventory._username = decodedSerializedInventory.substring(userIdStart1 + "<username>".length(), userIdEnd1);

                    try {
                        deserializedInventory._accessType = AccessType.valueOf(decodedSerializedInventory.substring(accessTypeStart + "<accessType>".length(), accessTypeEnd));
                    } catch (IllegalArgumentException var24) {
                        SmartLogger.getLogger().log(Level.WARNING, "Invalid access type in serialized inventory.", var24);
                    }
                }
            }

            return deserializedInventory;
        }
    }

    public DoorInfo get_doorused() {
        return _doorused;
    }

    public void set_doorused(DoorInfo _doorused) {
        this._doorused = _doorused;
    }

}

