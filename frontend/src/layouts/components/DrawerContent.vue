<script setup>
import {
  VerticalNavLink,
  VerticalNavSectionTitle,
} from '@layouts'
</script>

<template>
    <ul>
        <VerticalNavSectionTitle :item="{ heading: '인가/인증' }" />
        <VerticalNavLink
            :item="{
                title: '회원관리',
                to: '/memberManagements',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '대시보드' }" />
        <VerticalNavLink
            :item="{
                title: '포인트 조회',
                to: '/pointViews',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '구독자 목록',
                to: '/fetchSubscriberLists',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '통계 열람',
                to: '/authorStatistics',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '집필 관리' }" />
        <VerticalNavLink
            :item="{
                title: '도서',
                to: '/books',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '원고',
                to: '/manuscripts',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '포인트 관리' }" />
        <VerticalNavLink
            :item="{
                title: '포인트',
                to: '/points',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '구독자 관리' }" />
        <VerticalNavLink
            :item="{
                title: '구독관리',
                to: '/subscribeManagements',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '독서관리',
                to: '/readingManagements',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '포인트 요청 관리',
                to: '/pointRequestManagements',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '도서 구매 관리',
                to: '/bookPurchaseManagements',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '서재 플랫폼' }" />
        <VerticalNavLink
            :item="{
                title: '도서 출간',
                to: '/bookPublications',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '플랫폼 관리',
                to: '/platformManagements',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '작가 관리' }" />
        <VerticalNavLink
            :item="{
                title: '작가',
                to: '/authorRegistrations',
            }"
        />
    </ul>
</template>
