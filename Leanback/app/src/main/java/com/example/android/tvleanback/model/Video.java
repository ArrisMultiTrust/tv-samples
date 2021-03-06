/*
 * Copyright (c) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.tvleanback.model;

import android.media.MediaDescription;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Video is an immutable object that holds the various metadata associated with a single video.
 */
public final class Video implements Parcelable {
    public final long id;
    public final String category;
    public final String title;
    public final String description;
    public final String bgImageUrl;
    public final String cardImageUrl;
    public final String videoUrl;
    public final String studio;
    public final String license;
    public final String authtoken;
    public final String drmScheme;
    public final String asset;
    public final String entitlement;
    public final String policy;

    private Video(
            final long id,
            final String category,
            final String title,
            final String desc,
            final String videoUrl,
            final String bgImageUrl,
            final String cardImageUrl,
            final String studio,
            final String license,
            final String authtoken,
            final String drmScheme,
            final String asset,
            final String entitlement,
            final String policy) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.description = desc;
        this.videoUrl = videoUrl;
        this.bgImageUrl = bgImageUrl;
        this.cardImageUrl = cardImageUrl;
        this.studio = studio;
        this.license = license;
        this.authtoken = authtoken;
        this.drmScheme = drmScheme;
        this.asset = asset;
        this.entitlement = entitlement;
        this.policy = policy;
    }

    protected Video(Parcel in) {
        id = in.readLong();
        category = in.readString();
        title = in.readString();
        description = in.readString();
        bgImageUrl = in.readString();
        cardImageUrl = in.readString();
        videoUrl = in.readString();
        studio = in.readString();
        license = in.readString();
        authtoken = in.readString();
        drmScheme = in.readString();
        asset = in.readString();
        entitlement = in.readString();
        policy = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public boolean equals(Object m) {
        return m instanceof Video && id == ((Video) m).id;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(category);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(bgImageUrl);
        dest.writeString(cardImageUrl);
        dest.writeString(videoUrl);
        dest.writeString(studio);
        dest.writeString(license);
        dest.writeString(authtoken);
        dest.writeString(drmScheme);
        dest.writeString(asset);
        dest.writeString(entitlement);
        dest.writeString(policy);
    }

    @Override
    public String toString() {
        String s = "Video{";
        s += "id=" + id;
        s += ", category='" + category + "'";
        s += ", title='" + title + "'";
        s += ", videoUrl='" + videoUrl + "'";
        s += ", bgImageUrl='" + bgImageUrl + "'";
        s += ", cardImageUrl='" + cardImageUrl + "'";
        s += ", studio='" + cardImageUrl + "'";
        s += ", license='" + license +  "'";
        s += ", authtoken='" + authtoken +  "'";
        s += ", drmscheme='" + drmScheme + "'";
        s += ", asset='" + asset + "'";
        s += ", entitlement='" + entitlement + "'";
        s += ", policy='" + policy + "'";
        s += "}";
        return s;
    }

    // Builder for Video object.
    public static class VideoBuilder {
        private long id;
        private String category;
        private String title;
        private String desc;
        private String bgImageUrl;
        private String cardImageUrl;
        private String videoUrl;
        private String studio;
        private String license;
        private String authtoken;
        private String drmScheme;
        private String asset;
        private String entitlement;
        private String policy;

        public VideoBuilder id(long id) {
            this.id = id;
            return this;
        }

        public VideoBuilder category(String category) {
            this.category = category;
            return this;
        }

        public VideoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public VideoBuilder description(String desc) {
            this.desc = desc;
            return this;
        }

        public VideoBuilder videoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
            return this;
        }

        public VideoBuilder bgImageUrl(String bgImageUrl) {
            this.bgImageUrl = bgImageUrl;
            return this;
        }

        public VideoBuilder cardImageUrl(String cardImageUrl) {
            this.cardImageUrl = cardImageUrl;
            return this;
        }

        public VideoBuilder studio(String studio) {
            this.studio = studio;
            return this;
        }

        public VideoBuilder license(String license) {
            this.license = license;
            return this;
        }

        public VideoBuilder authtoken(String authtoken){
            this.authtoken = authtoken;
            return this;
        }

        public VideoBuilder drmScheme(String drmScheme){
            this.drmScheme = drmScheme;
            return this;
        }

        public VideoBuilder asset(String asset){
            this.asset = asset;
            return this;
        }

        public VideoBuilder entitlement(String entitlement){
            this.entitlement = entitlement;
            return this;
        }

        public VideoBuilder policy(String policy){
            this.policy = policy;
            return this;
        }

        public Video buildFromMediaDesc(MediaDescription desc) {
            return new Video(
                    Long.parseLong(desc.getMediaId()),
                    "", // Category - not provided by MediaDescription.
                    String.valueOf(desc.getTitle()),
                    String.valueOf(desc.getDescription()),
                    "", // Media URI - not provided by MediaDescription.
                    "", // Background Image URI - not provided by MediaDescription.
                    String.valueOf(desc.getIconUri()),
                    String.valueOf(desc.getSubtitle()),
                    "",
                    "",
                    "",
                    "",
                    "",
                    "");
        }

        public Video build() {
            return new Video(
                    id,
                    category,
                    title,
                    desc,
                    videoUrl,
                    bgImageUrl,
                    cardImageUrl,
                    studio,
                    license,
                    authtoken,
                    drmScheme,
                    asset,
                    entitlement,
                    policy);
        }
    }
}
