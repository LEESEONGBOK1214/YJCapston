import styled from "styled-components";

export const PosNavbarWrap = styled.div`
    a {
        color: black;
    }
    a:hover {
        color: darkgray;
    }
    pos-item:hover {
        color: darkgray;
    }
    .chatlist {
        position: fixed;
        z-index: 1000;
    }
    nav {
        position: fixed;
        display: flex;
        z-index: 4;
        top: 0;
        left: 0;
        right: 0;
        padding: 1rem;
        font-weight: bold;
        background-color: #41d1e1;
        height: 20px;
        color: white;
        margin-top: 146px;
    }
    ul {
        width: 100%;
        position: relative;
        list-style: none;
        height: 100%;
        margin-left: -20px;
    }
    .pos-item {
        position: relative;
        height: 100%;
        width: 25%;
        float: left;
        text-align: center;
    }
    .pos-item1 {
        position: relative;
        height: 100%;
        width: 25%;
        float: left;
        color: black;
        text-align: center;
    }
    pos-item1:hover {
        color: darkgray;
    }
`;
