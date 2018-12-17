package org.poem.constant;

/**
 *
 */
public enum ErrorCode {
    NotExists(1000),
    Existed(1001),
    Invalid(1002),
    Expired(1003),
    IpLimited(1004),
    PhoneLimited(1005),
    AccountInvalid(1006),
    PasswordError(1007),
    PasswordError3Times(1008),
    LoginNameIsLocked(1009),
    TransformFailed(1010),
    NoPermission(1011),
    StatusIllegal(1012),
    UploadToHDFSFail(1013),
    SubmitJobToOozieServerFail(1014),
    BuildWorkFlowError(1015),
    ParseXMLFail(1016),
    FileNotFound(1017),
    ExistsChildren(1018),
    OperateIllegal(1019),
    CreateAnalyzeTableFail(1020),
    TokenNotExists(1100),
    TokenExpired(1101),
    RefreshTokenNotExists(1102),
    RefreshTokenExpired(1103),
    ThirdRequestFailed(2000),
    BindPlatformAccount(2001),
    ThirdUserNotExists(2002),
    ContainsSensitiveWords(2100),
    FileTypeIllegal(2201),
    DataTypeIllegel(2202),
    NoRelativeMetaData(5001),
    RelativeMetaDataNotExists(5002),
    RelativeMetaDataFieldNotExists(5003),
    SystemConfigError(9997),
    ParamError(9998),
    UnknownError(9999),
    ExistLogin(10000);
    private int errorCode = 0;

    ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
