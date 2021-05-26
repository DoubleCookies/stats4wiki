import './App.css';
import React from 'react';

function App() {
    return (
        <div className="app">
            <header className="app-header">
                Stats4wiki<br/>~(˘▾˘~)
            </header>
            <main className="main-content">
                <h2 className="main-headers">О чём этот проект?</h2>
                В данном проекте собираются данные по топ-50 самых скачиваемых и <i>залайканных</i> уровней
                (как в целом, так и для отдельной категории "демонических" уровней), после чего формируется
                таблица, которая затем размещается на Geometry Dash вики. Параллельно с этим генерируется
                содержимое для пары шаблонов, которые используются в статьях про самые скачиваемые уровни.
                <br/>
                В итоге получается своего рода полуавтоматическое обновление топов и шаблонов (и, как бонус,
                на вики хранится история изменений этих топов).
                <h2 className="main-headers">Как он работает?</h2>
                Всё относительно просто:
                <ul>
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
                <h2 className="main-headers">Есть ли планы на будущее?</h2>
                <ul>
                    <li>Можно будет пройтись по старым коммитам, собрать информацию по прошлым месяцам/годам
                        и построить сводную табличку/график/<i>другую штуку</i>.</li>
                    <li>Добавить иллюстраций. Все же любят картинки?</li>
                </ul>
            </main>
            <footer className="footer-block">
                <div>2018-2021 <span role="img" aria-label="cookieEmoji">🍪</span></div>
            </footer>
        </div>
    );
}

export default App;