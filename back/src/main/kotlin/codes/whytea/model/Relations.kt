package codes.whytea.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Swipes: IntIdTable(){
    val swiper = reference("swiped", Users).index()
    val swipee = reference("swipee", Users).index()
}

class Swipe(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<Swipe>(Swipes)
    var swiper by User referencedOn Users.id
    var swipee by User referencedOn Users.id
}

object Matches: IntIdTable(){
    val first = reference("first", Users)
    val second = reference("second", Users)
}

class Match(id: EntityID<Int>): IntEntity(id){
    companion object : IntEntityClass<Match>(Matches)
    val first by User referencedOn Users.id
    var second by User referencedOn Users.id
}