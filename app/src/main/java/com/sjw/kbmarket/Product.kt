package com.sjw.kbmarket

import android.content.Context
import android.content.res.Resources
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream
import java.text.DecimalFormat

@Parcelize
data class Product(
    val productNumber: Int,
    val productName: String,
    val productDescription: String,
    val seller: String,
    val price: String,
    val location: String,
    var favorites: Int,
    val chats: Int,
    val imageId: Int,
    var position: Int = 0,
    var isChecked: Boolean = false
) : Parcelable {
    companion object {
        fun readExcelData(context: Context): MutableList<Product> {
            val dec = DecimalFormat("#,###")
            val productList = mutableListOf<Product>()

            // assets 폴더에서 엑셀 파일 열기
            val inputStream: InputStream = context.assets.open("dummy_data.xlsx")
            val workbook = WorkbookFactory.create(inputStream)
            val sheet = workbook.getSheetAt(0) // 첫 번째 시트 가져오기

            // 두 번째 행부터 데이터를 읽기 (첫 번째 행은 헤더)
            for (row in sheet.drop(2)) {
                val productNumber = row.getCell(0).numericCellValue.toInt()
                // 상품 번호가 1~10 사이일 때만 처리
                if (productNumber in 1..10) {
                    val productName = row.getCell(2).stringCellValue
                    val productDescription = row.getCell(3).stringCellValue
                    val seller = row.getCell(4).stringCellValue
                    val price = row.getCell(5).numericCellValue
                    val address = row.getCell(6).stringCellValue
                    val likes = row.getCell(7).numericCellValue.toInt()
                    val chats = row.getCell(8).numericCellValue.toInt()

                    val imgId = context.resources.getIdentifier("sample${productNumber}", "drawable" , context.packageName)

                    // Product 객체 생성 및 리스트에 추가
                    val product = Product(
                        productNumber,
                        productName,
                        productDescription,
                        seller,
                        "${dec.format(price)}원",
                        address,
                        likes,
                        chats,
                        imgId
                    )
                    productList.add(product)
                }
            }

            workbook.close()
            inputStream.close()

            return productList
        }

    }
}
