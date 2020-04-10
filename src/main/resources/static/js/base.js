var apiurl = '';

/*global*/
$(function () {
    //tab
    $('#mAccordion [data-tab-href]').each(function () {
        var $e = $(this).parent();
        var url = $(this).val();
        var text = $e.text();
        $e.click(function () {
            var $tab = $('#mTab');
            if ($tab.tabs('exists', text)) {
                $tab.tabs('select', text);
            }
            else {
                $tab.tabs('add', {
                    title: text,
                    content: '<iframe src="' + url + '" frameborder="0" scrolling="no" style="width:100%;height:99%"></iframe>',
                    closable: true
                });
            }
        });
    });
});
/*com*/
var com = {
    updatePwd: function () {
        $('#dlg_update_pwd').dialog('open');
    },
    signOut: function () {
        $.messager.confirm('提示', '您确定退出登录吗?', function (r) {
            if (r) {
                $.post('/admin/logout', null, function () {
                    location.href = '/admin/home';
                })
            }
        });
    },
    post: function (_url, _data, _fn) {
        var index = layer.load(2);
        $.ajax({
            type: "post",
            url: _url,
            data: _data,
            dataType: "json",
            success: function (resp) {
                alert.toast(resp.message);
                if (_fn) _fn(resp);
            },
            complete: function () {
                layer.close(index);
            },
            error: function (XHR) {

            }
        });
    }
}

var moduleForm = {
    submit: function (_frm, _dlg, _dg) {
        var nodes = $('#tree').tree('getChecked');
        var perm = '';
        for (var i = 0; i < nodes.length; i++) {
            if (perm != '') perm += ',';
            if (nodes[i].id == 0) continue;
            perm += nodes[i].id;
            var node1 = $('#tree').tree('getParent', nodes[i].target);          //得到父节点
            perm += "," + node1.id;
        }
        $("#Permission").val(perm);
        $(_frm || '.form').form('submit', {
            onSubmit: function () {
                var validate = $(this).form('enableValidation').form('validate');
                if (validate)
                    layer.load(2);
                return validate;
            },
            success: function (data) {
                data = $.parseJSON(data);
                layer.closeAll();
                if (_dlg && data.success) {
                    dlg.close(_dlg);
                }
                if (_dg && data.success) {
                    $(_dg).datagrid('reload');
                }
                alert.toast(data.message);
            }
        });
    },
    reset: function (_frm) {
        $(_frm || '.form').form('clear');
    }
};

var form = {
    submit: function (_frm, _dlg, _dg) {
        $(_frm || '.form').form('submit', {
            onSubmit: function () {
                var validate = $(this).form('enableValidation').form('validate');
                if (validate)
                    layer.load(2);
                return validate;
            },
            success: function (data) {
                data = $.parseJSON(data);
                layer.closeAll();
                if (_dlg && data.success) {
                    dlg.close(_dlg);
                }
                if (_dg && data.success) {
                    $(_dg).datagrid('reload');
                }
                alert.toast(data.message);
            }
        });
    },
    reset: function (_frm) {
        $(_frm || '.form').form('clear');
    }
};

var alert = {
    toast: function (_msg) {
        $.messager.show({
            title: '提示',
            msg: _msg,
            showType: 'slide',
            timeout: 1500,
            style: {
                right: '',
                top: document.body.scrollTop + document.documentElement.scrollTop,
                bottom: ''
            }
        });
    }
};

//弹出框
var dlg = {
    open: function (_dlg, _url) {
        $(_dlg).dialog('open');
        if (_url) $(_dlg).dialog('refresh', _url);
    },
    close: function (_dlg) {
        $(_dlg).dialog('close');
    },
    open2: function (_dg, _dlg, _url) {
        var row = $(_dg).datagrid('getSelected');
        if (row) {
            $(_dlg).dialog('open');
            if (_url) $(_dlg).dialog('refresh', _url + '?id=' + row.id);
        }
        else {
            alert.toast('请选择一行数据');
        }
    }
};

//数据表格
var dg = {
    edit2: function (_dg, _dlg, _url) {
        var row = $(_dg).datagrid('getSelections');
        debugger
        if (row.length > 0) {
            var result = _url.match(new RegExp("[\?\&][^\?\&]+=[^\?\&]+", "g"));
            var ids = [];
            for (var i = 0, j = row.length; i < j; i++) {
                ids.push(row[i].id);
            }
            ids.join(',');
            if (result == null) {
                $(_dlg).dialog('open').dialog('refresh', _url + '?ids=' + ids);
            } else {
                $(_dlg).dialog('open').dialog('refresh', _url + '&ids=' + ids);
            }
        }
        else {
            alert.toast('请选择一行数据');
        }
    },
    edit: function (_dg, _dlg, _url) {
        var row = $(_dg).datagrid('getSelected');
        if (row) {
            var result = _url.match(new RegExp("[\?\&][^\?\&]+=[^\?\&]+", "g"));
            if (result == null) {
                $(_dlg).dialog('open').dialog('refresh', _url + '?id=' + row.id);
            } else {
                $(_dlg).dialog('open').dialog('refresh', _url + '&id=' + row.id);
            }
        }
        else {
            alert.toast('请选择一行数据');
        }
    },
    del: function (_dg, _url, _title) {
        var row = $(_dg).datagrid('getSelected');
        if (row) {
            console.log(row);
            $.messager.confirm('提示', _title || '您确定删除此条数据吗?', function (r) {
                if (r) {
                    com.post(_url, {id: row.id}, function (resp) {
                        if (resp.success)
                            $(_dg).datagrid('reload');
                    })
                }
            });
        }
        else {
            alert.toast('请选择一行数据');
        }
    },
    query: function (_dg, pars) {
        var req = new Object();
        for (var i = 0; i < pars.length; i++) {
            var name = pars[i].split(',')[0];
            var val;
            if (pars[i].split(',').length > 1) {
                switch (eval(pars[i].split(',')[1])) {
                    case 1:
                        val = $('#query_' + name + '').combobox('getValue');
                        break;
                }
            }
            else {
                val = $('#query_' + name + '').textbox('getText');
            }
            req[name] = val;
        }
        $(_dg).datagrid('load', req);
    },
    img: function (_dg) {
        var row = $(_dg).datagrid('getSelected');
        if (row) {
            if (!row.CashUrl) {
                alert.toast('用户尚未上传提现二维码');
                return;
            }
            var host = 'http://localhost:54511/';
            $('#win').html('<img src="' + host + row.CashUrl + '" style="max-width:260px" />');
            $('#win').window({
                title: '提现二维码',
                width: 300,
                height: 300,
                modal: true,
                collapsible: false,
                minimizable: false,
                maximizable: false
            });
        }
        else {
            alert.toast('请选择一行数据');
        }
    }
};

//格式化
var formatter = {
    time: function (val, row) {
        var date = new Date(parseInt(val.replace("/Date(", "").replace(")/", ""), 10));
        var fmt = 'yyyy-MM-dd hh:mm:ss';
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(
                RegExp.$1,
                (date.getFullYear() + "").substr(4 - RegExp.$1.length)
            );
        }
        var o = {
            "M+": date.getMonth() + 1,
            "d+": date.getDate(),
            "h+": date.getHours(),
            "m+": date.getMinutes(),
            "s+": date.getSeconds()
        };
        for (var k in o) {
            if (new RegExp(k).test(fmt)) {
                var str = o[k] + "";
                fmt = fmt.replace(
                    RegExp.$1,
                    RegExp.$1.length === 1 ? str : formatter.fn.padLeftZero(str)
                );
            }
        }
        return fmt;
    },
    fn: {
        padLeftZero: function (str) {
            return ("00" + str).substr(str.length);
        }
    },
    time2: function (val, row) {
        if (val == null || val == "") return "";
        var now = new Date(val),
            y = now.getFullYear(),
            m = now.getMonth() + 1,
            d = now.getDate();
        return y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d) + " " + now.toTimeString().substr(0, 8);
    },
    time3: function (val, row) {
        if (val == 0) return '';
        var time = new Date(val);
        var y = time.getFullYear();
        var m = time.getMonth() + 1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
    },
    IsNull: function (val, row) {
        if (typeof val == "undefined" || val == null || val == "") return '无';
        return val;
    },
    YesOnNo: function (val, row) {
        if (val == "1") return '是';
        if (val == "0") return '否';
    },
    Locking: function (val, row) {
        if (val == "2") return '永久锁定';
        if (val == "1") return '暂时锁定';
        if (val == "0") return '正常';
    },
    Sex: function (val, row) {
        if (val == "1") return '<i class="fa fa-mars-stroke"></i> 男';
        if (val == "0") return '<i class="fa fa-venus"></i> 女';
    },
    img: function (val, row) {
        return '<img src="' + val + '" style="width:150px" />';
    },
    img2: function (val, row) {
        return '<img src="' + val + '" style="width:100px;height: 80px;" />';
    },
    OrderStatus: function (val, row) {
        if (val == "0") return '<span style="color:red">未付款</span>';
        if (val == "1") return '<span style="color:#00beea">未发货</span>';
        if (val == "2") return '待收货';
        if (val == "3") return '<span style="color:chartreuse">已完成</span>';
        if (val == "4") return '已取消';
    }, recordStatus: function (val) {
        if (val == 0) return "支出";
        if (val == 1) return "收入";
    },
    propLevel: function (val, row) {
        if (val == "0") return '暂无仙子';
        if (val == "1") return '金仙子';
        if (val == "2") return '木仙子';
        if (val == "3") return '水仙子';
        if (val == "4") return '火仙子';
        if (val == "5") return '土仙子';
    },
    userStatus: function (val, row) {
        if (val == "0") return '未激活';
        if (val == "1") return '已激活';
        if (val == "2") return '有效会员';
    },
    landStatus: function (val, row) {
        if (val == "0") return '生长中';
        if (val == "1") return '已枯萎';
    },
    harvestStatus: function (val, row) {
        if (val == "0") return '未收获';
        if (val == "1") return '已收获';
    },
    isPick: function (val, row) {
        if (val == "0") return '未采密';
        if (val == "1") return '已采密';
    },
    userProductStatus: function (val, row) {
        if (val == "0") return '生长中';
        if (val == "1") return '已转让';
        if (val == "2") return '转让中';
    },
    activationState: function (val, row) {
        if (val == "0") return '未激活';
        if (val == "1") return '已激活';
    },
    UserLevel: function (val, row) {
        if (val == "0") return '普通会员';
        if (val == "1") return '高级会员';
        if (val == "2") return '钻石会员';
        if (val == "3") return '皇冠会员';
        if (val == "4") return '金牌会员';
    },
    UserLevel: function (val, row) {
        if (val == "0") return '普通会员';
        if (val == "1") return '高级会员';
        if (val == "2") return '钻石会员';
        if (val == "3") return '皇冠会员';
        if (val == "4") return '金牌会员';
    },
    UserType: function (val, row) {
        if (val == "0") return '普通用户';
        if (val == "1") return '平台用户';
    },
    identityState: function (val, row) {
        if (val == "0") return '未认证';
        if (val == "1") return '待审核';
        if (val == "2") return '认证成功';
        if (val == "3") return '认证失败';
    },
    tradeType: function (val, row) {
        if (val == "0") return '卖出';
        if (val == "1") return '买入';
    },
    tradeIsDel: function (val, row) {
        if (val == "0") return '正常';
        if (val == "1") return '已撤销';
    },
    tradeIsLock: function (val, row) {
        if (val == "0") return '匹配中';
        if (val == "1") return '交易锁定';
    },
    tradeMatchStatus: function (val, row) {
        if (val == "0") return '待转让';
        if (val == "1") return '待付款';
        if (val == "2") return '待收款';
        if (val == "3") return '已完成';
        if (val == "4") return '申诉中';
        if (val == "5") return '已取消';
    },
    tradeMatchType: function (val, row) {
        if (val == "0") return '正常订单';
        if (val == "1") return '申诉订单';
    },
    userUnlockStatus: function (val, row) {
        if (val == "0") return '审核中';
        if (val == "1") return '已完成';
        if (val == "2") return '已驳回';
    },
    userWithdrawalStatus: function (val, row) {
        if (val == "0") return '申请中';
        if (val == "1") return '已完成';
        if (val == "2") return '已驳回';
    }, RecordType: function (val, row) {
        if (val == 1000) return "后台充值";
        if (val == 1001) return "后台减扣";
        if (val == 1002) return "实名赠送";
        if (val == 1003) return "兑换宠物";
        if (val == 1004) return "预约消耗";
        if (val == 1005) return "匹配过期";
        if (val == 1006) return "抽奖消耗";
        if (val == 1007) return "抽奖获得";
        if (val == 1008) return "宠物转让";
        if (val == 1009) return "合约收益";
        if (val == 1010) return "团队奖励";
        if (val == 1011) return "胡萝卜交易";
        if (val == 1012) return "申请解锁";

    }, PaymentType: function (val) {
        if (val == 0) return "胡萝卜";
        if (val == 1) return "积分";
        if (val == 2) return "信誉分";
        if (val == 3) return "合约收益";
    }, RecordStatus: function (val) {
        if (val == 0) return "支出";
        if (val == 1) return "收入";
    }, prizeType: function (val) {
        if (val == 0) return "积分奖励";
        if (val == 1) return "谢谢惠顾";
        if (val == 2) return "自定义奖品";
    }, prizeRecordType: function (val) {
        if (val == 0) return "未中奖";
        if (val == 1) return "中奖";
    }, prizeRecordStatus: function (val) {
        if (val == 0) return "未中奖";
        if (val == 1) return "未处理";
        if (val == 2) return "已处理";
    }, HangBuyStatus: function (val) {
        if (val == 0) return "预约中";
        if (val == 1) return "预约成功";
        if (val == 2) return "预约失败";
    },HangSellStatus: function (val) {
        if (val == 0) return "待转让";
        if (val == 1) return "转让中";
        if (val == 2) return "已转让";
    },isOpen: function (val) {
        if (val == 0) return "未开放";
        if (val == 1) return "开放中";
    },msgType: function (val) {
        if (val == 0) return "公告列表";
        if (val == 1) return "弹窗公告";
    }
};

//管理员
var admin = {
    login: function (_frm, _src) {
        $(_frm || '.form').form('submit', {
            onSubmit: function () {
                var validate = $(this).form('enableValidation').form('validate');
                return validate;
            },
            success: function (data) {
                data = $.parseJSON(data);
                layer.closeAll();
                if (_src) {
                    if (data.success)
                        debugger;
                    location.href = _src;
                }
                alert.toast(data.message);
            }
        });
    }
}