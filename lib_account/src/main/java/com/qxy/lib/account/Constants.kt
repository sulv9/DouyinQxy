package com.qxy.lib.account

const val USER_IS_LOGIN = "secure_user_is_login"
const val USER_AUTH_CODE = "secure_user_auth_code"

const val ACCESS_TOKEN = "access_token"
const val ACCESS_TOKEN_SAVED_TIMESTAMP = "access_token_saved_timestamp"
const val ACCESS_TOKEN_EXPIRES_IN = "access_token_expires_in"

const val KEY_CLIENT_TOKEN = "key_client_token"
const val KEY_CLIENT_TOKEN_SAVE_TIME = "key_client_token_save_time"

const val video_scope = "im.share,aweme.share,h5.share,aweme.capture,video.data,video.list," +
        "video.create,video.delete,video.search,video.search.comment,"
const val user_scope = "user_info,following.list,fans.list,fans.check,renew_refresh_token,"
const val interact_scope = "item.comment,"
const val data_scope = "data.external.item,discovery.ent,star_top_score_display,star_tops," +
        "star_author_score_display,data.external.sdk_share,hotsearch,data.external.billboard_live," +
        "data.external.billboard_hot_video,data.external.billboard_music," +
        "data.external.billboard_prop,data.external.billboard_topic," +
        "data.external.billboard_game,data.external.billboard_drama," +
        "data.external.billboard_car,data.external.billboard_amusement," +
        "data.external.billboard_cospa,data.external.billboard_food," +
        "data.external.billboard_travel,data.external.billboard_stars," +
        "data.external.billboard_sport,"
const val special_scope = "open.get.ticket,market.service.user,trial.whitelist,poi.cps.common," +
        "micapp.is_legal,life.capacity.member,incremental_authorization"
const val scope = video_scope + interact_scope + user_scope + data_scope + special_scope