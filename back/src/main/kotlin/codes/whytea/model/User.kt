package codes.whytea.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.min

object Users : IntIdTable(){
    val name = varchar("name", length = 50)
    val email = varchar("email", length = 50).uniqueIndex()
    val vkUserId = integer("Vk_User_ID").uniqueIndex()

    override val primaryKey = PrimaryKey(id, name = "PK_User_ID")
}

class User(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<User>(Users)
    var name by Users.name
    var email by Users.email
    val skills by Skill referrersOn Skills.owner
}

object Skills : IntIdTable() {
    val owner = reference("owner", Users)
    val name = varchar("name", length = 15)
    val experience = enumeration("experience", SkillExperience::class)
}

class Skill(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<Skill>(Skills)
//    var owner by User referencedOn Users.id
    var name by Skills.name
    var experience by Skills.experience
}

enum class SkillExperience {
    NEWBIE, BEGINNER, INTERMEDIATE, ADVANCED, PROFESSIONAL
}
