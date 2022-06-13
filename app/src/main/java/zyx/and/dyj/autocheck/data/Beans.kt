package zyx.and.dyj.autocheck.data

data class XXTResult(
    val msg: String,
    val success: Boolean
)

/**
 * @param id 身份证号
 */
data class StuData(
    val name: String,
    val phoneNumber: String,

    val id: String,
    val pwd: String,
    val xueyuan: String,
    val zhuanye: String,
    val banji: String
)

class FormData : ArrayList<FormDataItem>()

data class FormDataItem(
    val linkInfo: LinkInfo,
    val compt: String,
    val loginUserForValue: Boolean,
    val layoutRatio: Int,
    val relationValueConfig: RelationValueConfig,
    val alias: Int,
    val optionalScope: OptionalScope,
    val id: Int,
    val fields: List<Field>,
    val inDetailGroupIndex: Int,
    val fromDetail: Boolean,
    val isShow: Boolean,
    val hasAuthority: Boolean,
    val formula: Formula,
    val latestValShow: Boolean,
    val formulaEdit: FormulaEdit,
    val level: Int,
    val locationScope: LocationScope,
    val distanceRange: Int,
    val defaultValueConfig: Int,
    val locationValue: Int,
    val inDetailGroupGeneralId: Int,
    val optionScoreShow: Boolean,
    val optionSort: OptionSort,
    val optionScoreUsed: Boolean,
    val otherAllowed: Boolean,
    val optionsLoadFromUrl: OptionsLoadFromUrl,
    val optionColor: Boolean,
    val optionBindInfo: OptionBindInfo
)

data class LinkInfo(
    val linkFormType: String,
    val condFields: List<Any>,
    val linkFormId: Int,
    val linkFormValueFieldCompt: String,
    val linkFormIdEnc: String,
    val linkFormValueFieldId: Int,
    val linked: Boolean
)

data class RelationValueConfig(
    val condFieldId: Int,
    val type: Int,
    val `open`: Boolean
)

data class OptionalScope(
    val options: String,
    val type: Int
)

data class Field(
    val hasDefaultValue: Boolean,
    val visible: Boolean,
    val editable: Boolean,
    var values: List<MutableMap<String, String>>,
    val verify: Verify,
    val tip: Tip,
    val label: String,
    val sweepCode: Boolean,
    val defaultValueStr: String,
    val fieldType: FieldType,
    val codeChangeable: Boolean
)

data class Formula(
    val selIndex: Int,
    val calculateFieldId: Int,
    val status: Boolean
)

data class FormulaEdit(
    val formula: String
)

data class LocationScope(
    val mapValue: List<Any>,
    val linkedInfo: LinkedInfo,
    val defaultRange: Int,
    val select: Boolean,
    val type: Int
)

data class OptionSort(
    val id: Int,
    val sort: String
)

data class OptionsLoadFromUrl(
    val isLoadFromUrl: Boolean,
    val response: List<Any>,
    val url: List<Any>,
    val urlHeaders: List<Any>
)

data class OptionBindInfo(
    val bindFormIdEnc: String,
    val bindFieldId: Int,
    val bindFieldIdx: Int,
    val bindFormType: String,
    val isBinded: Boolean,
    val bindFormId: Int,
    val originalOptions: List<OriginalOption>,
    val bindFieldCompt: String
)

data class Verify(
    val charLimit: CharLimit,
    val regularExpress: RegularExpress,
    val unique: Unique,
    val format: Format,
    var required: Required
)

data class Tip(
    val imgs: List<Any>,
    val text: String
)

data class FieldType(
    val type: String
)

data class CharLimit(
    val size: Int,
    val `open`: Boolean
)

data class RegularExpress(
    val errorTip: String,
    val express: String
)

data class Unique(
    val errMsg: String,
    val `open`: Boolean
)

data class Format(
    val type: String
)

class Required

class LinkedInfo

data class OriginalOption(
    val idArr: List<Any>,
    val score: Int,
    val checked: Boolean,
    val title: String
)