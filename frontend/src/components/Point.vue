<template>
    <div>
        <Number
            label="PointAccountId"
            v-model="value.pointAccountId"
            :editMode="editMode"
        />
        <String
            label="UserId"
            v-model="value.userId"
            :editMode="editMode"
        />
        <Number
            label="CurrentPoint"
            v-model="value.currentPoint"
            :editMode="editMode"
        />
        <Number
            label="TotalCharged"
            v-model="value.totalCharged"
            :editMode="editMode"
        />
        <Number
            label="TotalUsed"
            v-model="value.totalUsed"
            :editMode="editMode"
        />
        <Boolean
            label="IsktCustomer"
            v-model="value.isktCustomer"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'Point',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "points",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
