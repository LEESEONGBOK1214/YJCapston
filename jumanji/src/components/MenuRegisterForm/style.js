import styled from "styled-components";

export const MenuRegisterWrap = styled.div`
background: #F2F2F2;
    @font-face {
        font-family: "Wemakeprice-Bold";
        src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10-21@1.0/Wemakeprice-Bold.woff")
            format("woff");
        font-weight: normal;
        font-style: normal;
    }
    
    > header {
        padding-left: 5%;
        width: 100%;
        color: black;
        display: flex;
        padding-top: 30px;
        padding-bottom: 30px;
        font-weight: 700;
        font-size: 33px;
        font-family: "Wemakeprice-Bold";
        position: relative;
        background-color: white;
        z-index: 10;
        > .movemainpage {
            border: none;
            color: black;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            cursor: pointer;
            opacity: 1;
        }
    }

    .topimg {
        margin-top: 85px;
        height: 300px;
        width: 100%;
        margin-bottom: 50px;
        position: relative;
    }

    .topimg-text {
        font-family: "Wemakeprice-Bold";
        width: 100%;
        position: absolute;
        text-align: center;
        font-size: 60px;
        color: white;
        transform: translateY(-230px);
    }

    .total-body {      
        position: relative;
        margin-left: 50%;
        transform: translateX(-200px);
        z-index: 2;
    }

    .label {
        margin-top: 20px;
        margin-bottom: 2px;
        font-size: 18px;
        font-weight: bold;
    }

    .input-box {
        width: 400px;
        height: 36px;
        border-radius: 5px;
        border: 1px solid gray;
    }
    .input-box-area {
        width: 400px;
        height: 56px;
        border-radius: 5px;
        border: 1px solid gray;
    }

    .phone-box {
        width: 80px;
        height: 30px;
        border-radius: 5px;
        border: 1px solid gray;
    }

    .phone-box1 {
        width: 80px;
        height: 34px;
        border-radius: 5px;
        border: 1px solid gray;
    }

    .button3 {
        height: 50px;
        width: 200px;
        margin-left: 5px;
        margin-right: 5px;
        background: #1785f2;
        color: white;
        border: 1px solid gray;
        font-size: 16px;
    }
    .button4 {
        height: 50px;
        width: 200px;
        margin-left: 5px;
        margin-right: 5px;
        background: white;
        color: black;
        border: 1px solid black;
        font-size: 16px;
    }
    .button1 {
        height: 40px;
        width: 60px;
        margin-right: 5px;
        transform: translateY(2px);
        transform: translateX(-60px);
        border-radius: 0 5px 5px 0;
        border: 1px solid gray;
        background: #1785f2;
        color: white;
    }
    .button2 {
        height: 40px;
        width: 60px;
        margin-right: 5px;
        transform: translateY(2px);
        transform: translateX(-60px);
        border-radius: 0 5px 5px 0;
        border: 1px solid gray;
        background: #1785f2;
        color: white;
    }
    .button-box {
        width: 480px;
        margin-top: 60px;
        text-align: center;
        transform: translateX(-36px);
        padding-bottom: 70px;
        margin-top: 60px;
    }
    .check-box {
        
        margin-left: 10px;
        width: 25px;
        height: 25px;
        transform: translateY(5px);
    }
    .in-won {
        display: inline-block;
        transform: translateX(-30px);
    }
    .in-minute {
        display: inline-block;
        transform: translateX(-30px);
    }
`;