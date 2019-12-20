/*
* 在引入本js前，先引入crypto的js库 和 md5js 库
* */

var md5_16_key = '';
var md5_16_iv = '';
var key = '';
var iv = '';
var errorMsg = '';


// 通过md5加密 生成16位的key+iv
function createKeyIv(passwordStr) {
    var md5_32_key = hex_md5(passwordStr);
    var md5_32_iv = hex_md5(md5_32_key);

    md5_16_key = md5_32_key.substr(8, 16);
    md5_16_iv = md5_32_iv.substr(8, 16);

    // 判断key 和iv 是否16位
    if (md5_16_key.length != 16 || md5_16_iv.length != 16){
        errorMsg = '加密失败,请刷新尝试~';
        return false;
    }

    key = CryptoJS.enc.Utf8.parse(md5_16_key);
    iv  = CryptoJS.enc.Utf8.parse(md5_16_iv);
}


// 加密
// return  false or 加密后的字符串
function Encrypt(enc_str, passwordStr) {

    if (!enc_str || !passwordStr){
        errorMsg = '参数不能为空~';
        return false;
    }else {

        // 生成key + iv
        createKeyIv(passwordStr);
    }

    var source = enc_str;
    var password = CryptoJS.enc.Utf8.parse(source);
    var encrypted = CryptoJS.AES.encrypt(password, key, { iv: iv,mode:CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7});
    // var encryptedStr = encrypted.ciphertext.toString();
    return encrypted;
}

// 解密
function Decrypt(encrypted, passwordStr) {

    if (!encrypted || !passwordStr){
        errorMsg = '参数不能为空~';
        return false;
    }else {

        // 生成key + iv
        createKeyIv(passwordStr);
    }


    var encrypted = encrypted;
    var decrypted = CryptoJS.AES.decrypt(encrypted, key, { iv: iv,mode:CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7});  //CryptoJS.pad.Pkcs7

    var result =  decrypted.toString(CryptoJS.enc.Utf8);
    if (!result){
        errorMsg = '密码有误~';
        return false;
    }else{
        return result;
    }

}
