package edu.ucne.registroticket.Data.Local.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registroticket.Data.Local.Dao.TicketDao
import edu.ucne.registroticket.Data.Local.Entities.ConversationEntity
import edu.ucne.registroticket.Data.Local.Entities.TicketEntity

@Database(
    entities = [
        TicketEntity::class,
        ConversationEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class TicketDb : RoomDatabase(){
    abstract fun ticketDao(): TicketDao
}