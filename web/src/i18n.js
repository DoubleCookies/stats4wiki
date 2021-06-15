import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import detector from 'i18next-browser-languagedetector';

const resources = {
    en: {
        translation: {
            aboutTitle: "What is this project about?",
            aboutText: "I collect data about top-50 most downloaded and most liked levels in Geometry Dash " +
                "(overall and for \"demon\" difficulty levels). After that I form table and " +
                "post it on Geometry Dash Wiki (ru). At the same time I create data for two templates which are used " +
                "in articles about most downloaded levels.<1/>" +
                "In result there is semi-automatic articles and templates update (and as a bonus, Wiki contains " +
                "changes for these pages).",
            workTitle: "How does it work?",
            workList: "Everything is pretty simple and consecutive:<1>" +
                "<0>Fetch data from Geometry Dash servers and get page (10 levels). Depending on type " +
                "(downloads/likes) form two levels lists.</0>" +
                "<0>Stop processing pages when we have 50 demon levels (automatically " +
                "we will have 50 levels with every difficulty).</0>" +
                "<0>Both lists split for another two lists  — for all levels and for demon-only.</0>" +
                "<0>Transform every list in txt format " +
                "(with nuances like template types, presence or absence of links, dividers, etc.)</0>" +
                "<0>Process text for templates and save it.</0>" +
                "<0><2>Done!</2></0>" +
                "</1>",
            plansTitle: "Any plans?",
            plansList: "<0><1>I can check old commits, collect data from old months/years and " +
                "build graph/table/<2>other thing</2>.</1>" +
                "<1>Add illustrations. Everybody likes illustrations I guess?</1></0>",
        }
    },
    ru: {
        translation: {
            aboutTitle: "О чём этот проект?",
            aboutText: "В нём собираются данные по топ-50 самых скачиваемых и залайканных уровней в Geometry Dash " +
                "(как в целом, так и для категории \"демонических\" уровней), после чего формируется " +
                "таблица, которая затем размещается на Geometry Dash вики. Параллельно с этим генерируется " +
                "содержимое для пары шаблонов, которые используются в статьях про самые скачиваемые уровни. <1/>" +
                "В итоге получается своего рода полуавтоматическое обновление топов и шаблонов (и, как бонус, " +
                "на вики хранится история изменений этих топов).",
            workTitle: "Как он работает?",
            workList: "Всё довольно просто и последовательно:<1>" +
                "<0>Обращаемся к серверам Geometry Dash и получаем страницу (10 уровней). В зависимости " +
                "от типа (кол-во скачиваний/лайков) формируем два набора уровней.</0>" +
                "<0>Останавливаемся в тот момент, когда в списке будет 50 демонов (при этом автоматически " +
                "выполняется условие, что у нас будет 50 любых уровней).</0>" +
                "<0>Из каждого списка формируем ещё два — для всех видов уровней и только для демонов.</0>" +
                "<0>Каждый список переводим в текстовый формат (с учётом различных нюансов по типу шаблонов " +
                "сложности, наличия или отсутствия ссылок, разделителей и т. д.)</0>" +
                "<0>Формируем текст для шаблонов и также сохраняем.</0>" +
                "<0><2>Готово!</2></0>" +
                "</1>",
            plansTitle: "Есть ли планы на будущее?",
            plansList: "<0><1>Можно будет пройтись по старым коммитам, собрать информацию по прошлым месяцам/годам " +
                "и построить сводную табличку/график/<2>другую штуку</2>.</1>" +
                "<1>Добавить иллюстраций. Все же любят картинки?</1></0>",
        }
    }
}

i18n.use(detector).use(initReactI18next).init({
    fallbackLng: 'en',
    resources,
})

export default i18n;