// 根据单选框触发是否提交事件!
$(function () {
    $("input[type=checkBox]").click(function () {
        if (this.checked == true) {
            toString($(this).attr('id'));
        } else {
            toString($(this).attr('id')+"s");
        }
    })
})


function toString(index) {
    $.ajax({
        method: 'POST',
        url: "/portal/toDo",
        data: {
            "str": index
        },
        success: function (data) {
            console.log(data);
        }
    });
}