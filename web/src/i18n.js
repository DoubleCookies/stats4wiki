import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import detector from 'i18next-browser-languagedetector';

const resources = {
    en: {
        translation: {
            aboutTitle: "What is this project about?"
        }
    },
    ru: {
        translation: {
            aboutTitle: "О чём этот проект?"
        }
    }
}

i18n.use(detector).use(initReactI18next).init({
        fallbackLng: 'en',
        resources: resources,
    })