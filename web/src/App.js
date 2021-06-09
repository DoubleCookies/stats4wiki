import './App.css';
import React from 'react';
import { Trans, useTranslation } from "react-i18next";
import i18n from "./i18n";

function App() {
    const {t} = useTranslation();
    const changeLanguage = () => {
        console.log('inside!')
        let lng = 'en';
        if (i18n.language === 'en') {
            lng = 'ru';
        }
        i18n.changeLanguage(lng).then();
    }

    return (
        <div className="app">
            <header className="app-header">
                Stats4wiki<br/>~(˘▾˘~)
                <div className="button-container">
                    <button className="lang-button" aria-label="Switch language (en/ru)" title="Switch language (en/ru)" onClick={() => changeLanguage()}>
                        🌎
                    </button>
                </div>
            </header>
            <main className="main-content">
                <h2 className="main-headers">{t("aboutTitle")}</h2>
                {t("aboutStart")}
                <br/>
                {t("aboutFinish")}
                <h2 className="main-headers">{t("workTitle")}</h2>
                <Trans i18nKey="workList">
                    Всё довольно просто и последовательно:
                    <ul className="tight-list">
                        <li>Обращаемся к серверам Geometry Dash и получаем страницу (10 уровней). В зависимости
                            от типа (кол-во скачиваний/лайков) формируем два набора уровней.</li>
                        <li>Останавливаемся в тот момент, когда в списке будет 50 демонов (при этом автоматически
                            выполняется условие, что у нас будет 50 любых уровней).</li>
                        <li>Из каждого списка формируем ещё два — для всех видов уровней и только для демонов.</li>
                        <li>Каждый список переводим в текстовый формат (с учётом различных нюансов по типу шаблонов
                            сложности, наличия или отсутствия ссылок, разделителей и т. д.)</li>
                        <li>Формируем текст для шаблонов и также сохраняем.</li>
                        <li><b>Готово!</b></li>
                    </ul>
                </Trans>
                <h2 className="main-headers">Есть ли планы на будущее?</h2>
                <Trans i18nKey="plansList">
                    <ul className="tight-list">
                        <li>Можно будет пройтись по старым коммитам, собрать информацию по прошлым месяцам/годам
                            и построить сводную табличку/график/<i>другую штуку</i>.</li>
                        <li>Добавить иллюстраций. Все же любят картинки?</li>
                    </ul>
                </Trans>
            </main>
            <footer className="footer-block">
                <div>2018-2021 <span role="img" aria-label="cookieEmoji">🍪</span></div>
            </footer>
        </div>
    );
}

export default App;
