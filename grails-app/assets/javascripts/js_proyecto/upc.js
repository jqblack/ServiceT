/**
 * Created by Pabel on 8/3/2017.
 */


alert("hola mundo");


var value_0 = "0".charCodeAt(0);
var value_9 = "9".charCodeAt(0);
var value_hypen = "-".charCodeAt(0);
var value_sp = " ".charCodeAt(0);
function IsNumeric(param) {
    for (var i = 0; i < param.length; i++) {
        var value = param.charCodeAt(i);
        if (value < value_0 || value > value_9)
            return false;
    }
    return true;
}
function IsValidISBN(param) {
    for (var i = 0; i < param.length; i++) {
        var value = param.charCodeAt(i);
        if (value == value_hypen || value == value_sp)continue;
        if (value < value_0 || value > value_9)
            return false;
    }
    return true;
}
function GetEANCheckDigit(param) {
    var sum = 0;
    var odd_parity = true;
    for (var i = param.length - 1; i >= 0; i--) {
        if (odd_parity)
        {
            sum += 3 * (param.charCodeAt(i) - value_0);

        }
        else
        {
            sum += param.charCodeAt(i) - value_0;

        }
        odd_parity = !odd_parity;
    }
    var check_digit = 10 - (sum % 10);
    if (check_digit == 10)check_digit = 0;
    return String.fromCharCode(check_digit + value_0);
}


function GetISBNCheckDigit(param) {
    var str = "";
    for (var i = 0; i < param.length; i++) {
        var value = param.charCodeAt(i);
        if (value >= value_0 && value <= value_9)
            str += String.fromCharCode(value);
    }
    var sum = 0;
    for (var i = 0; i < str.length; i++) {
        var value = str.charCodeAt(i) - value_0;
        sum += value * (10 - i);
    }
    var check_digit = 11 - sum % 11;
    if (check_digit == 10)return "X"; else if (check_digit == 11)return "0"; else return String.fromCharCode(check_digit + value_0);
}
$(document).ready(function () {
    $('#upc-calculate').click(calc_upca);
    $('#ean-calculate').click(calc_ean);
    $('#scc-calculate').click(calc_scc);
    $('#sscc-calculate').click(calc_sscc);
    $('#ean8-calculate').click(calc_ean8);
    $('#bln-calculate').click(calc_bln);
    $('#isbn-calculate').click(calc_isbn);
    $('#upca_convert').click(convert_upca);
    $('#upce_convert').click(convert_upce);
    $('#upc-number').focus(focus_upc);
    $('#ean-number').focus(focus_ean);
    $('#scc-number').focus(focus_scc);
    $('#sscc-number').focus(focus_sscc);
    $('#ean8-number').focus(focus_ean8);
    $('#bln-number').focus(focus_bln);
    $('#isbn-number').focus(focus_isbn);
    $('#upca_convert_number').focus(focus_upca_convert);
    $('#upce_convert_number').focus(focus_upce_convert);
    function focus_upca_convert(event) {
        $('form button').attr('type', 'button');
        $('#upca_convert').attr('type', 'submit');
    }

    function focus_upce_convert(event) {
        $('form button').attr('type', 'button');
        $('#upce_convert').attr('type', 'submit');
    }

    function focus_isbn(event) {
        $('form button').attr('type', 'button');
        $('#isbn-calculate').attr('type', 'submit');
    }

    function focus_bln(event) {
        $('form button').attr('type', 'button');
        $('#bln-calculate').attr('type', 'submit');
    }

    function focus_ean8(event) {
        $('form button').attr('type', 'button');
        $('#ean8-calculate').attr('type', 'submit');
    }

    function focus_sscc(event) {
        $('form button').attr('type', 'button');
        $('#sscc-calculate').attr('type', 'submit');
    }

    function focus_ean(event) {
        $('form button').attr('type', 'button');
        $('#ean-calculate').attr('type', 'submit');
    }

    function focus_upc(event) {
        $('form button').attr('type', 'button');
        $('#upc-calculate').attr('type', 'submit');
    }

    function focus_scc(event) {
        $('form button').attr('type', 'button');
        $('#scc-calculate').attr('type', 'submit');
    }

    function calc_upca(event) {
        event.preventDefault();
        $('.error').html('');
        $('#upc-number-checkdigit').val('');
        var value = $('#upc-number').val();
        if (value.length != 11) {
            $('#upc-number').after('<span class="error"><br/>Error: invalid length - ' + 'requiring exact 11 digits.</span>');
            $('#upc-number').focus();
            return;
        }
        var check_digit = GetEANCheckDigit(value);
        $('#upc-number-checkdigit').val(check_digit);
    }

    function calc_ean(event) {
        event.preventDefault();
        $('.error').html('');
        $('#ean-number-checkdigit').val('');
        var value = $('#ean-number').val();
        if (value.length != 12) {
            $('#ean-number').after('<span class="error"><br/>Error: invalid length - ' + 'requiring exact 12 digits.</span>');
            $('#ean-number').focus();
            return;
        }
        var check_digit = GetEANCheckDigit(value);
        $('#ean-number-checkdigit').val(check_digit);
    }

    function calc_scc(event) {
        event.preventDefault();
        $('.error').html('');
        $('#scc-number-checkdigit').val('');
        var value = $('#scc-number').val();
        if (value.length != 13) {
            $('#scc-number').after('<span class="error"><br/>Error: invalid length - ' + 'requiring exact 13 digits.</span>');
            $('#scc-number').focus();
            return;
        }
        var check_digit = GetEANCheckDigit(value);
        $('#scc-number-checkdigit').val(check_digit);
    }

    function calc_sscc(event) {
        event.preventDefault();
        $('.error').html('');
        $('#sscc-number-checkdigit').val('');
        var value = $('#sscc-number').val();
        if (value.length != 17) {
            $('#sscc-number').after('<span class="error"><br/>Error: invalid length - ' + 'requiring exact 17 digits.</span>');
            $('#sscc-number').focus();
            return;
        }
        var check_digit = GetEANCheckDigit(value);
        $('#sscc-number-checkdigit').val(check_digit);
    }

    function calc_ean8(event) {
        event.preventDefault();
        $('.error').html('');
        $('#ean8-number-checkdigit').val('');
        var value = $('#ean8-number').val();
        if (value.length != 7) {
            $('#ean8-number').after('<span class="error"><br/>Error: invalid length - ' + 'requiring exact 7 digits.</span>');
            $('#ean8-number').focus();
            return;
        }
        var check_digit = GetEANCheckDigit(value);
        $('#ean8-number-checkdigit').val(check_digit);
    }

    function calc_bln(event) {
        event.preventDefault();
        $('.error').html('');
        $('#bln-number-checkdigit').val('');
        var value = $('#bln-number').val();
        if (value.length != 16) {
            $('#bln-number').after('<span class="error"><br/>Error: invalid length - ' + 'requiring exact 16 digits.</span>');
            $('#bln-number').focus();
            return;
        }
        var check_digit = GetEANCheckDigit(value);
        $('#bln-number-checkdigit').val(check_digit);
    }

    function calc_isbn(event) {
        event.preventDefault();
        $('.error').html('');
        $('#isbn-number-checkdigit').val('');
        var value = $('#isbn-number').val();
        if (value.length == 0 || !IsValidISBN(value)) {
            $('#isbn-number').after('<span class="error"><br/>Error: invalid ISBN number.</span>');
            $('#isbn-number').focus();
            return;
        }
        var check_digit = GetISBNCheckDigit(value);
        $('#isbn-number-checkdigit').val(check_digit);
    }

    function convert_upca(event) {
        event.preventDefault();
        $('.error').html('');
        $('#upce_convert_result').val('');
        var value = $('#upca_convert_number').val();
        if (!IsNumeric(value) || value.length != 11) {
            $('#upca_convert_number').after('<span class="error"><br/>Error: The input for UPC-A number must contain exact 11 digits.</span>');
            $('#upca_convert_number').focus();
            return;
        }
        var number_system = value.substr(0, 1);
        if (number_system != "0") {
            $('#upca_convert_number').after('<span class="error"><br/>Error: the UPC-A number must begin with a number system 0.</span>');
            $('#upca_convert_number').focus();
            return;
        }
        var manufacturer_code = value.substr(1, 5);
        var product_code = value.substr(6, 5);
        var three_end_digits_of_manufacturer_code = manufacturer_code.substr(2, 3);
        if (three_end_digits_of_manufacturer_code == "000" || three_end_digits_of_manufacturer_code == "100" || three_end_digits_of_manufacturer_code == "200") {
            if (eval(product_code) >= 0 && eval(product_code) <= 999) {
                var upc_e = manufacturer_code.substr(0, 2) + product_code.substr(2, 3) + manufacturer_code.substr(2, 1);
                $('#upce_convert_result').val(upc_e);
            } else {
                $('#upca_convert_number').after('<span class="error"><br/>Error: This UPC-A does not have counterpart UPC-E number.</span>');
            }
            return;
        }
        var two_end_digits_of_manufacturer_code = manufacturer_code.substr(3, 2);
        if (two_end_digits_of_manufacturer_code == "00") {
            if (eval(product_code) >= 0 && eval(product_code) <= 99) {
                var upc_e = manufacturer_code.substr(0, 3) + product_code.substr(3, 2) + "3";
                $('#upce_convert_result').val(upc_e);
            } else {
                $('#upca_convert_number').after('<span class="error"><br/>Error: This UPC-A does not have counterpart UPC-E number.</span>');
            }
            return;
        }
        if (manufacturer_code.substr(4, 1) == "0") {
            if (eval(product_code) >= 0 && eval(product_code) <= 9) {
                var upc_e = manufacturer_code.substr(0, 4) + product_code.substr(4, 1) + "4";
                $('#upce_convert_result').val(upc_e);
            } else {
                $('#upca_convert_number').after('<span class="error"><br/>Error: This UPC-A does not have counterpart UPC-E number.</span>');
            }
            return;
        }
        if (eval(product_code) >= 5 && eval(product_code) <= 9) {
            var upc_e = manufacturer_code.substr(0, 5) + product_code.substr(4, 1);
            $('#upce_convert_result').val(upc_e);
        }
        else {
            $('#upca_convert_number').after('<span class="error"><br/>Error: This UPC-A does not have counterpart UPC-E number.</span>');
        }
        return;
    }

    function convert_upce(event) {
        event.preventDefault();
        $('.error').html('');
        $('#upca_convert_result').val('');
        var value = $('#upce_convert_number').val();
        if (!IsNumeric(value) || value.length != 6) {
            $('#upce_convert_number').after('<span class="error"><br/>Error: The input for UPC-E number must contain exact 6 digits</span>');
            $('#upce_convert_number').focus();
            return;
        }
        var manufacturer_code, product_code;
        var lastdigit = value.substr(5, 1);
        switch (lastdigit) {
            case"0":
            case"1":
            case"2":
                manufacturer_code = value.substr(0, 2) + value.substr(5, 1) + "00";
                product_code = "00" + value.substr(2, 3);
                break;
            case"3":
                manufacturer_code = value.substr(0, 3) + "00";
                product_code = "000" + value.substr(3, 2);
                break;
            case"4":
                manufacturer_code = value.substr(0, 4) + "0";
                product_code = "0000" + value.substr(4, 1);
                break;
            default:
                manufacturer_code = value.substr(0, 5);
                product_code = "0000" + value.substr(5, 1);
                break;
        }
        $('#upca_convert_result').val("0" + manufacturer_code + product_code);
    }
});
