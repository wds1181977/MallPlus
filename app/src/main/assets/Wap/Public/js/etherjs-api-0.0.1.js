/*
* 在引入本js前，先引入etherjs库
* */

if (typeof web3 !== 'undefined') {
    web3 = new Web3(web3.currentProvider);
} else {
    var serverUrl = "http://127.0.0.1:8545";//web3服务器
    // set the provider you want from Web3.providers
    web3 = new Web3(new Web3.providers.HttpProvider(serverUrl));
}


/**
 * 获取余额
 * @param address 钱包地址
 * @return 余额   （当结果为非数字，表示有异常或错误的时候）
 */
function getBalance(address) {
    if (false == checkAdress(address)){
        console.log('参数有误:');
        return '参数有误';
    }

    try{
        return web3.fromWei(web3.eth.getBalance(address), 'ether');
    }catch (e){
        console.log('ThrowError:'+e.message);
        return e.name + e.message;
    }

}


/**
 * 转账
 * */
function transaction(from, to, value, gasPrice, gas, data, transactionNonce, callback) {
    var transObj = {
        from: from,
        to: to,
        value: value,
        gasPrice: gasPrice,
        gas: gas,
        data: data/*,
        nonce: transactionNonce*/
    };

    console.log('transObj:'+ JSON.stringify(transObj));
    //return false;


    web3.eth.sendTransaction(transObj, function(error, hash){
        console.log('transError1:'+error);
        console.log('TransHash1:'+hash);
        if (error != null){
            TransError = error;
            console.log('transError2:'+error);
            callback(false);
            //return false;

        }else {

            console.log('TransHash2:'+hash);
            callback(hash);
            //return hash;
        }


    });

}


/**
 * 创建交易数据
 * */
function createTransaction(from, to, value) {
    return {
        from: from,
        to: to,
        value: value
    }
}


/**
 * 获取gasPrice
 * */
function getGasPrice() {
    return web3.eth.gasPrice.toString(10);
    // console.log(gasPrice);
}


/**
 * 获取estimateGas
 * */
function getEstimateGas(from, to, value){
    // var transaction = createTransaction(from, to, value);
    return web3.eth.estimateGas({
            from: from,
            to: to,
            value: value
        });


}

/**
 * 获取某个地址的交易次数  getTransactionNonce
 * */
function getTransactionNonce(address) {
    return web3.eth.getTransactionCount(address)+1;
}


/**
 * 解锁账户
 * @return  true or errorMsg
 * */
function unLockAccountFun(address, password) {
    try {
        return web3.personal.unlockAccount(address, password, 20);
    }catch (e){
        console.log('ThrowError:'+e.message);
        return e.name + e.message;
    }
}

/**
 * 锁账户
 * @return  true or errorMsg
 * */
function lockAccountFun(address) {
    try {
        return web3.personal.lockAccount(address);
    }catch (e){
        console.log('ThrowError:'+e.message);
        return e.name + e.message;
    }
}


/**
 * 判断一个以太坊地址是不是有效地址
 * @param address 钱包地址
 * @return bool( true 是  false不是 其他为异常信息)
 * */
function checkAdress(address) {
    return web3.isAddress(address);
}


/**
 * 判断一个以太坊地址是不是合约
 * @param address 钱包地址
 * @return bool( true 是合约地址  false普通地址 其他为异常信息)
 * */
function checkContactAdress(address) {
    try{
        var code = web3.eth.getCode(address);
        if(code === '0x') return false;
        else return true;
    }catch (e){
        console.log('ThrowError:'+e.message);
        return e.name + e.message;
    }
}

/**
 *判断是否是数字
 * @return bool( true 是  false不是)
 **/
function isRealNum(val){
    // isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
    if(val === "" || val ==null){
        return false;
    }
    if(!isNaN(val)){
        return true;
    }else{
        return false;
    }
}