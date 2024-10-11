package Impl.Messages.Update

import Classtype.Vaccine
import Impl.Messages.TSMSPMessage
import Impl.{STATUS_OK, TSMSPReply}
import Tables.{UserTokenTable, VaccTable}
import Utils.{DBUtils, IOUtils}
import org.joda.time.DateTime

import scala.util.Try

/**
 * 更新第三次疫苗信息
 *
 * @param userToken
 * @param vacc
 */
case class UpdateThirdVaccMessage(userToken: String, vacc: String) extends TSMSPMessage {
  override def reaction(now: DateTime): Try[TSMSPReply] = Try {
    val phone = UserTokenTable.checkPhone(userToken).get
    val newVacc = IOUtils.toObject[Vaccine](Some(vacc)).get.get
    DBUtils.exec(VaccTable.updateThirdVacc(phone, newVacc).get)
    TSMSPReply(STATUS_OK, Globals.GlobalVariables.updateSuccess)
  }
}
