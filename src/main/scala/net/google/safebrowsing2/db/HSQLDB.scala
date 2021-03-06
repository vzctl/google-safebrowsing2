package net.google.safebrowsing2.db

import java.sql.Connection
import java.util.Date
import scala.collection.mutable
import javax.sql.DataSource
import util.JdbcTemplate
import util.LiteDataSource
import net.google.safebrowsing2.MacKey
import net.google.safebrowsing2.Hash
import net.google.safebrowsing2.Chunk
import net.google.safebrowsing2.Status
import org.joda.time.Period
import org.joda.time.DateTime
import org.joda.time.DateTime
import net.google.safebrowsing2.Expression
import util.Logging
import org.joda.time.Duration

/**
 * HSQLDB Storage class used to access the database.
 * 
 * @see DBI
 */
class HSQLDB(jt: JdbcTemplate, tablePrefix: String) extends DBI(jt, tablePrefix) {
  def this(ds: () => Connection, tablePrefix: String) = this(new JdbcTemplate(ds), tablePrefix)
  def this(ds: LiteDataSource, tablePrefix: String) = this(new JdbcTemplate(ds), tablePrefix)
  def this(ds: DataSource, tablePrefix: String) = this(new JdbcTemplate(ds), tablePrefix)

  import jt._

  override def createTableFullHashes = {
    logger.debug("Creating table: "+TABLE_PREFIX+"FullHashes")
    val schema = """
		CREATE TABLE """+TABLE_PREFIX+"""FullHashes (
			pkiFullHashesID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1 INCREMENT BY 1) PRIMARY KEY,
			iAddChunkNum INT,
			sHash VARCHAR( 64 ),
			sList VARCHAR( 50 ),
			dtLastUpdate TIMESTAMP NOT NULL
		)
	"""

    execute(schema)
    
    val index = """
		CREATE UNIQUE INDEX IDX"""+TABLE_PREFIX+"""FullHashes_Unique ON """+TABLE_PREFIX+"""FullHashes (
			iAddChunkNum,
			sHash,
			sList
		)
	"""
    execute(index)
  }

  override def createTableFullHashErrors = {
    logger.debug("Creating table: "+TABLE_PREFIX+"FullHashErrors")
    val schema = """
		CREATE TABLE """+TABLE_PREFIX+"""FullHashErrors (
			pkiFullHasheErrorsID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1 INCREMENT BY 1) PRIMARY KEY,
			dtLastAttempt TIMESTAMP NOT NULL,
			dtNextAttempt TIMESTAMP NOT NULL,
			iErrorCount INT NOT NULL,
			sPrefix VARCHAR( 64 )
		)
	"""

    execute(schema)
  }
}