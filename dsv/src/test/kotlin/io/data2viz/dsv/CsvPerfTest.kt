package io.data2viz.dsv


data class MatchScore(val match_year:String, val tourney_order:String, val tourney_name:String, val tourney_id:String, val tourney_slug:String, val tourney_location:String, val tourney_dates:String, val tourney_singles_draw:String, val tourney_doubles_draw:String, val tourney_conditions:String, val tourney_surface:String, val tourney_fin_commit:String, val tourney_long_slug:String, val tourney_round_name:String, val round_order:String, val match_order:String, val winner_name:String, val winner_player_id:String, val winner_slug:String, val loser_name:String, val loser_player_id:String, val loser_slug:String, val match_score:String, val match_stats_url_suffix:String)

@JsName("parseToModel")
fun parseToModel(content: String) =
        Dsv().parseRows(content)
                .drop(1)
                .map {
                    MatchScore(
                            it[0],
                            it[1],
                            it[2],
                            it[3],
                            it[4],
                            it[5],
                            it[6],
                            it[7],
                            it[8],
                            it[9],
                            it[10],
                            it[11],
                            it[12],
                            it[13],
                            it[14],
                            it[15],
                            it[16],
                            it[17],
                            it[18],
                            it[19],
                            it[20],
                            it[21],
                            it[22],
                            it[23]
                    )}
                .toTypedArray()

